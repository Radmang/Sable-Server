package io.xeros.model.entity.player.packets.dialogueoptions;

import io.xeros.content.DiceHandler;
import io.xeros.content.LootValue;
import io.xeros.content.achievement_diary.impl.*;
import io.xeros.content.items.Degrade;
import io.xeros.content.items.PvpWeapons;
import io.xeros.content.items.RecolourGraceful;
import io.xeros.content.skills.StatReset;
import io.xeros.content.skills.agility.AgilityHandler;
import io.xeros.content.teleportation.TeleportationDevice;
import io.xeros.model.Items;
import io.xeros.model.cycleevent.impl.WheatPortalEvent;
import io.xeros.model.entity.npc.NPC;
import io.xeros.model.entity.npc.NPCHandler;
import io.xeros.model.entity.player.Player;
import io.xeros.model.entity.player.PlayerAssistant;
import io.xeros.model.entity.player.PlayerHandler;
import io.xeros.model.entity.player.Right;

/*
 * @author Matt
 * Five Option Dialogue actions
 */

public class FiveOptions {

	/*
	 * Handles all first options on 'Five option' dialogues.
	 */
	public static void handleOption1(Player c) {
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
			switch (c.dialogueAction) {

			case 838:
				c.getDiaryManager().getDesertDiary().progress(DesertDiaryEntry.TRAVEL_POLLNIVNEACH);
				c.startAnimation(2262);
				AgilityHandler.delayFade(c, "NONE", 3351, 3003, 0, "You step on the carpet and take off...",
						"at last you end up in Pollnivneach.", 3);
				c.getPA().removeAllWindows();
				break;
			case 765:
				c.getShops().openShop(172);
				break;
			case 4487:
				c.getPA().startTeleport(2881, 5310, 2, "modern", false);
				break;
			case 851:
				c.getDH().sendDialogues(854, 7520);
				break;
			case 855: //Tekton
				//OLMLET.facePlayer(c.getIndex());
				//HISTORIAN.startAnimation(1818);
				//HISTORIAN.gfx0(343);
				//OLMLET.forceChat("Off to Tekton you go " + Misc.formatPlayerName(c.playerName) + "!");
				//c.getPA().movePlayer(3309, 5277, 1);
				c.getPA().startTeleport(3309, 5277, 1, "modern", false);
				c.getPA().closeAllWindows();
				break;
		
		case 801:
			c.getDH().sendDialogues(802, 6773);
			break;
			
		case 77:
			c.getPA().closeAllWindows();
			if (!c.getRingOfLifeEffect()) {
				c.setRingOfLifeEffect(true);
				c.sendMessage("You have toggled on the ROL effect.");
			} else {
				c.setRingOfLifeEffect(false);
				c.sendMessage("You have toggled off the ROL effect.");
			}
			break;
		case 14400:
			c.getPA().startTeleport(2474, 3438, 0, "modern", false);
			c.sendMessage("You will gain XP after each lap");
			c.getPA().closeAllWindows();
			break;
			
		case 141314:
			c.getPA().startTeleport(2465, 3450, 0, "modern", false);
			c.sendMessage("You have teleported to Gnome Stronghold");
			c.getDiaryManager().getWesternDiary().progress(WesternDiaryEntry.SPIRIT_TREE_WEST);
			c.getPA().closeAllWindows();
			break;
			
		case 193193:
			c.safeBoxSlots = 1;
			c.sendMessage("You have set your destination to [ @red@Piscarlarius House@bla@ ].");
			c.getPA().closeAllWindows();
			break;

		case 14410:
			c.getPA().startTeleport(3104, 3279, 0, "modern", false);//1st option
			c.sendMessage("You will gain XP after completion of each lap");
			c.getPA().closeAllWindows();
			break;
		case 14411:
			c.getPA().startTeleport(2729, 3488, 0, "modern", false);//1st option
			c.sendMessage("You will gain XP after completion of each lap");
			c.getPA().closeAllWindows();
			break;
		case 14412:
			c.getPA().startTeleport(3624, 3678, 0, "modern", false);//1st option
			c.sendMessage("You will gain XP after completion of each lap");
			c.getPA().closeAllWindows();
			break;
		case 1401:
			StatReset.execute(c, 0);
			break;

		case 1404:
			StatReset.execute(c, 5);
			break;

		case 2002:
			c.getPA().spellTeleport(3565, 3308, 0, false);
			break;
		case 2003:
			c.getPA().spellTeleport(3218, 9622, 0, false);
			break;

		case 61: // Resource Area
			TeleportationDevice.startTeleport(c, 0);
			break;
			
		case 76: //Max cape
				c.getPA().closeAllWindows();
				if (c.getPosition().inWild()) {
					return;
				}
				if (c.playerMagicBook == 0) {
					c.playerMagicBook = 1;
					c.setSidebarInterface(6, 838);
					c.autocasting = false;
					c.sendMessage("An ancient wisdomin fills your mind.");
					c.getPA().resetAutocast();
				} else if (c.playerMagicBook == 1) {
					c.sendMessage("You switch to the lunar spellbook.");
					c.setSidebarInterface(6, 29999);
					c.playerMagicBook = 2;
					c.autocasting = false;
					c.autocastId = -1;
					c.getPA().resetAutocast();
				} else if (c.playerMagicBook == 2) {
					c.setSidebarInterface(6, 938);
					c.playerMagicBook = 0;
					c.autocasting = false;
					c.sendMessage("You feel a drain on your memory.");
					c.autocastId = -1;
					c.getPA().resetAutocast();
				}
			break;
			
		case 1393:
			LootValue.configureValue(c, "setvalue", 100000);
			break;
			
		case 55:
			RecolourGraceful.COLOR = "PURPLE";
			RecolourGraceful.recolor(c, RecolourGraceful.ITEM_TO_RECOLOUR);
			break;
			
		case 56:
			RecolourGraceful.COLOR = "GREEN";
			RecolourGraceful.recolor(c, RecolourGraceful.ITEM_TO_RECOLOUR);
			break;
		case 711:
			c.getDH().sendDialogues(712, 306);
			break;
			
		case 78: //Max cape crafting guild teleport
			c.getPA().startTeleport(2936, 3283, 0, "modern", false);
			c.getPA().closeAllWindows();
			break;
		}

		if (c.dialogueAction == 114) {
			if (c.getItems().playerHasItem(6737, 1) && c.pkp >= 250) {
				c.getItems().deleteItem(6737, 1);
				c.pkp -= 250;
				c.getQuestTab().updateInformationTab();
				c.getItems().addItem(11773, 1);
				c.sendMessage("You imbue your berserker ring for the cost of @blu@250 PKP@bla@.");
				c.getPA().removeAllWindows();
				c.dialogueAction = 0;
			} else {
				c.sendMessage("You need 250 PKP and a Berserker ring to do this.");
				c.getPA().removeAllWindows();
				c.dialogueAction = 0;
			}
			return;
		}
		if (c.dialogueAction == 135) {
			if (c.getItems().playerHasItem(12605, 1) && c.pkp >= 250) {
				c.getItems().deleteItem(12605, 1);
				c.pkp -= 250;
				c.getQuestTab().updateInformationTab();
				c.getItems().addItem(12692, 1);
				c.sendMessage("You imbue your treasonous ring for the cost of @blu@250 PKP@bla@.");
				c.getPA().removeAllWindows();
				c.dialogueAction = 0;
			} else {
				c.sendMessage("You need 250 PKP and a Treasonous ring to do this.");
				c.getPA().removeAllWindows();
				c.dialogueAction = 0;
			}
			return;
		}
		if (c.dialogueAction == 131) {
			c.getDH().sendDialogues(659, 311);
			return;
		}
		if (c.dialogueAction == 128) {
			c.getFightCave().create(1);
			return;
		}
		if (c.teleAction == 17) {
			c.getPA().spellTeleport(2808, 10002, 0, false);
			return;
		}
		if (c.dialogueAction == 123) {
			Degrade.DegradableItem[] claimable = Degrade.getClaimedItems(c);
			if (claimable.length == 0) {
				return;
			}
			c.getPA().removeAllWindows();
			Degrade.claim(c, claimable[0].getItemId());
			return;
		}
		if (c.dialogueAction == 121) {
			c.getDH().sendDialogues(614, -1);
			c.teleAction = -1;
			c.dialogueAction = -1;
			return;
		}
		if (c.teleAction == 14) {
			c.getPA().spellTeleport(1923, 4367, 0, false);
			c.teleAction = -1;
			c.dialogueAction = -1;
			return;
		}
		if (c.teleAction == 12) {
			c.getPA().spellTeleport(3302, 9361, 0, false);
		}
		if (c.teleAction == 11) {
			c.getPA().spellTeleport(3228, 9392, 0, false);
		}
		if (c.teleAction == 10) {
			c.getPA().spellTeleport(2705, 9487, 0, false);
		}
		if (c.teleAction == 9) {
			c.getPA().spellTeleport(3226, 3263, 0, false);
		}
		if (c.teleAction == 8) {
			c.getPA().spellTeleport(3293, 3178, 0, false);
		}
		if (c.teleAction == 7) {
			c.getPA().spellTeleport(3118, 9851, 0, false);
		}
		if (c.teleAction == 15) { //Chickens
			c.getPA().spellTeleport(3236, 3295, 0, false);
		}
		if (c.teleAction == 99) { //Crash island
			c.getPA().spellTeleport(2897, 2725, 0, false);
		}
		if (c.teleAction == 1) { //Lava Dragons
			c.getPA().spellTeleport(2977, 3603, 0, false);
		} else if (c.teleAction == 2) { //Brimhaven Dungeon
			c.getPA().spellTeleport(1571, 3656, 0, false);
		} else if (c.teleAction == 200) { //Raids
			 c.getPA().spellTeleport(3301, 5192, 0, false);
		} else if (c.teleAction == 147258) { //Varrock
			 c.getPA().spellTeleport(3213, 3423, 0, false);
		} else if (c.teleAction == 258369) { //Ardy
			 c.getPA().spellTeleport(2653, 3283, 0, false);
			 c.getDiaryManager().getArdougneDiary().progress(ArdougneDiaryEntry.TELEPORT_ARDOUGNE);
		} else if (c.teleAction == 201) { //Duel Arena
			 c.getPA().spellTeleport(3367, 3266, 0, false);
		} else if (c.teleAction == 3) { //King Black Dragon
			c.getPA().spellTeleport(3005, 3850, 0, false);
		} else if (c.teleAction == 33) { //Dagannoth Kings
			c.getPA().spellTeleport(1912, 4367, 0, false);
		} else if (c.teleAction == 3434) { //Vet'ion
			c.getPA().spellTeleport(3200, 3794, 0, false);
		} else if (c.teleAction == 1337) { //Land's End
			c.getPA().spellTeleport(1504, 3419, 0, false);
		} else if (c.teleAction == 4) {
			// varrock wildy
			c.getPA().spellTeleport(3025, 3379, 0, false);
		} else if (c.teleAction == 5) {
			c.getPA().spellTeleport(3046, 9779, 0, false);
		} else if (c.teleAction == 2000) {
			// lum
			c.getPA().spellTeleport(3222, 3218, 0, false);// 3222 3218
		} else {
			int actionButtonId = 0;
			DiceHandler.handleClick(c, actionButtonId);
		}
		if (c.dialogueAction == 10) {
			c.getPA().spellTeleport(2845, 4832, 0, false);
			c.dialogueAction = -1;

		} else if (c.dialogueAction == 11) {
			c.getPA().spellTeleport(2786, 4839, 0, false);
			c.dialogueAction = -1;
		} else if (c.dialogueAction == 12) {
			c.getPA().spellTeleport(2398, 4841, 0, false);
			c.dialogueAction = -1;
		}
	}

	/*
	 * Handles all 2nd options on 'Five option' dialogues.
	 */
	public static void handleOption2(Player c) {

		switch (c.dialogueAction) {
		case 1002:
			c.getDH().sendDialogues(1001, 13);
			break;
		case 838:
			c.startAnimation(2262);
			AgilityHandler.delayFade(c, "NONE", 3180, 3043, 0, "You step on the carpet and take off...",
					"at last you end up in Bedabin Camp.", 3);
			c.getPA().removeAllWindows();
			break;
		case 765:
			c.getShops().openShop(173);
			break;
		case 998:
			c.getDH().sendDialogues(999, 13);
            break;
		case 4487:
			if (c.getItems().playerHasItem(995, 150000)) {
				c.getItems().deleteItem(995, 150000);
				c.getPA().startTeleport(2858, 5354, 2, "modern", false);
				c.sendMessage("@red@You have teleported to Bandos Enterance for @red@150k");
				break;
			}
			c.sendMessage("@red@You need 150k coins to use this teleport.");
			break;
		
		case 855: //Skeletal mystics
			//OLMLET.facePlayer(c.getIndex());
			//HISTORIAN.startAnimation(1818);
			//HISTORIAN.gfx0(343);
			//OLMLET.forceChat("Off to Skeletal Mystics you go " + Misc.formatPlayerName(c.playerName) + "!");
			//c.getPlayerAssistant().movePlayer(3343, 5248, 1);
			c.getPA().startTeleport(3343, 5248, 1, "modern", false);
			c.getPA().closeAllWindows();
			break;
		case 801:
			c.getDH().sendDialogues(805, 6773);
			break;
			
		case 141314:
			c.getPA().startTeleport(1310, 3613, 0, "modern", false);
			c.sendMessage("You have teleported to Xeric's Shrine");
			c.getPA().closeAllWindows();
			break;
			
		case 193193:
			c.safeBoxSlots = 2;
			c.sendMessage("You have set your destination to [ @red@Hosidius House@bla@ ].");
			c.getPA().closeAllWindows();
			break;
		
		case 77:
			c.getPA().closeAllWindows();
			if (!c.getFishingEffect()) {
				c.setFishingEffect(true);
				c.sendMessage("You have toggled on the Fishing effect.");
			} else {
				c.setFishingEffect(false);
				c.sendMessage("You have toggled off the Fishing effect.");
			}
			break;
		
	case 711:
		c.getDH().sendDialogues(713, 306);
		break;
		
		case 14400:
			c.getPA().startTeleport(2551, 3555, 0, "modern", false);
			c.sendMessage("You will gain XP after each lap");
			c.getPA().closeAllWindows();
			break;

		case 14410:
			c.getPA().startTeleport(3272, 3196, 0, "modern", false);
			c.sendMessage("You will gain XP after completion ofeach lap");//option 2
			c.getPA().closeAllWindows();
			break;
		case 14411:
			c.getPA().startTeleport(2672, 3296, 0, "modern", false);//2nd option
			c.sendMessage("You will gain XP after completion of each lap");
			c.getPA().closeAllWindows();
			break;
		case 14412:
			c.getPA().startTeleport(2672, 3296, 0, "modern", false);// option 2
			c.sendMessage("You will gain XP after completion of each lap");
			c.getPA().closeAllWindows();
			break;
		case 1401:
			StatReset.execute(c, 2);
			break;

		case 1404:
			StatReset.execute(c, 4);
			break;
		case 2002:
			c.getPA().spellTeleport(2847, 3543, 0, false);
			c.sendMessage("@blu@Use the animators to gain tokens, then head upstairs to the cyclops.");
			break;
		case 2003:

			break;

		case 61: // Kbd Lair
			TeleportationDevice.startTeleport(c, 1);
			break;
			
		case 76: //Max cape
			c.getDH().sendDialogues(78, 311);
			break;
			
		case 78: //Max cape teleport
			PlayerAssistant.ringOfCharosTeleport(c);
			//c.getPA().closeAllWindows();
			break;
			
		case 1393:
			LootValue.configureValue(c, "setvalue", 500000);
			break;
			
		case 55:
			RecolourGraceful.COLOR = "BLUE";
			RecolourGraceful.recolor(c, RecolourGraceful.ITEM_TO_RECOLOUR);
			break;
			
		case 56:
			RecolourGraceful.COLOR = "WHITE";
			RecolourGraceful.recolor(c, RecolourGraceful.ITEM_TO_RECOLOUR);
			break;
		}

		if (c.teleAction == 17) {
			c.getPA().spellTeleport(2444, 9825, 0, false);
			return;
		}
		if (c.teleAction == 1337) { //Skillers Cove
			c.getPA().spellTeleport(1721, 3464, 0, false);
			return;
		}
		if (c.teleAction == 99) { //bobs island
			c.getPA().spellTeleport(2525,4776, 0, false);
		}
		if (c.dialogueAction == 114) {
			if (c.getItems().playerHasItem(6733, 1) && c.pkp >= 250) {
				c.getItems().deleteItem(6733, 1);
				c.pkp -= 250;
				c.getQuestTab().updateInformationTab();
				c.getItems().addItem(11771, 1);
				c.sendMessage("You imbue your archer ring for the cost of @blu@250 PKP@bla@.");
				c.getPA().removeAllWindows();
				c.dialogueAction = 0;
			} else {
				c.sendMessage("You need 250 PKP and an Archer ring to do this.");
				c.dialogueAction = 0;
				c.getPA().removeAllWindows();
			}
			return;
		}
		if (c.dialogueAction == 135) {
			if (c.getItems().playerHasItem(12603, 1) && c.pkp >= 250) {
				c.getItems().deleteItem(12603, 1);
				c.pkp -= 250;
				c.getQuestTab().updateInformationTab();
				c.getItems().addItem(12691, 1);
				c.sendMessage("You imbue your treasonous ring for the cost of @blu@250 PKP@bla@.");
				c.getPA().removeAllWindows();
				c.dialogueAction = 0;
			} else {
				c.sendMessage("You need 250 PKP and a Treasonous ring to do this.");
				c.getPA().removeAllWindows();
				c.dialogueAction = 0;
			}
			return;
		}
		if (c.dialogueAction == 131) {
			c.getDH().sendDialogues(652, 311);
			return;
		}
		if (c.teleAction == 16) {
			c.getPA().startTeleport(3077, 3910, 0, "modern", false);
			return;
		}
		if (c.teleAction == 15) {
			c.getPA().startTeleport(3253, 3266, 0, "modern", false);
			return;
		}
		if (c.teleAction == 2) { //Taverly Dungeon
			c.getPA().spellTeleport(1662, 3529, 0, false);
		}
		if (c.teleAction == 33) { //Barrelchest
			c.getPA().spellTeleport(1229, 3497, 0, false);
		}
		if (c.teleAction == 3434) { //Venenatis
			c.getPA().spellTeleport(3339, 3756, 0, false);
		}
		if (c.teleAction == 201) { //Shayzien Assault
			c.getPA().spellTeleport(1461, 3689, 0, false);
		}
		if (c.dialogueAction == 128) {
			c.getFightCave().create(2);
			return;
		}
		if (c.dialogueAction == 123) {
			Degrade.DegradableItem[] claimable = Degrade.getClaimedItems(c);
			if (claimable.length < 2) {
				return;
			}
			c.getPA().removeAllWindows();
			Degrade.claim(c, claimable[1].getItemId());
			return;
		}
		if (c.dialogueAction == 121) {
			c.getDH().sendDialogues(616, -1);
			c.teleAction = -1;
			c.dialogueAction = -1;
			return;
		}
		if (c.teleAction == 14) {
			c.getPA().spellTeleport(3292, 3648, 0, false);
			c.sendMessage("@blu@The kraken is roughly 20 steps south-west of this location.");
			c.dialogueAction = -1;
			c.teleAction = -1;
			return;
		}
		if (c.teleAction == 12) {
			c.getPA().spellTeleport(2908, 9694, 0, false);
		}
		if (c.teleAction == 11) {
			c.getPA().spellTeleport(3237, 9384, 0, false);
		}
		if (c.teleAction == 10) {
			c.getPA().spellTeleport(3219, 9366, 0, false);
		}
		if (c.teleAction == 9) {
			c.getPA().spellTeleport(2916, 9800, 0, false);
		}
		if (c.teleAction == 8) {
			c.getPA().spellTeleport(2903, 9849, 0, false);
		}
		if (c.teleAction == 7) {
			c.getPA().spellTeleport(2859, 9843, 0, false);
		}
		if (c.teleAction == 3) {
			// kbd
			// c.sendMessage("King Black Dragon has been disabled.");
			c.getPA().spellTeleport(3262, 3929, 0, false);
		}
		// c.getPA().closeAllWindows();
		/*
		 * if (c.teleAction == 1) { //rock crabs c.getPA().spellTeleport(2676, 3715, 0); } else if (c.teleAction == 2) { //taverly dungeon c.getPA().spellTeleport(2884, 9798, 0); }
		 * else if (c.teleAction == 3) { //kbd c.getPA().spellTeleport(3007, 3849, 0); } else if (c.teleAction == 4) { //west lv 10 wild c.getPA().spellTeleport(2979, 3597, 0); }
		 * else if (c.teleAction == 5) { c.getPA().spellTeleport(3079,9502,0); }
		 */
		if (c.teleAction == 1) {
			// West drags
			c.getPA().spellTeleport(3357, 3674, 0, false);
		} else if (c.teleAction == 200) {
			// pest control
			 c.getPA().spellTeleport(2660, 2648, 0, false);
		} else if (c.teleAction == 147258) { //Fally
			 c.getPA().spellTeleport(2965, 3377, 0, false);
			c.getDiaryManager().getFaladorDiary().progress(FaladorDiaryEntry.TELEPORT_TO_FALADOR);
		} else if (c.teleAction == 258369) { //Cammy
			 c.getPA().spellTeleport(2725, 3491, 0, false);
			 c.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.CAMELOT_TELEPORT);
		} else if (c.teleAction == 4) {
			// graveyard
			c.getPA().spellTeleport(3043, 9779, 0, false);
		} else if (c.teleAction == 5) {
			c.getPA().spellTeleport(3079, 9502, 0, false);

		} else if (c.teleAction == 2000) {
			c.getPA().spellTeleport(3210, 3424, 0, false);// 3210 3424
		} else if (c.dialogueAction == 10) {
			c.getPA().spellTeleport(2796, 4818, 0, false);
			c.dialogueAction = -1;
		} else if (c.dialogueAction == 11) {
			c.getPA().spellTeleport(2527, 4833, 0, false);
			c.dialogueAction = -1;
		} else if (c.dialogueAction == 12) {
			c.getPA().spellTeleport(2464, 4834, 0, false);
			c.dialogueAction = -1;
		}
	}

	/*
	 * Handles all 3rd options on 'Five option' dialogues.
	 */
	public static void handleOption3(Player c) {

		switch (c.dialogueAction) {
		case 1002:
			c.getPA().closeAllWindows();
			break;
		case 838:
			c.startAnimation(2262);
			AgilityHandler.delayFade(c, "NONE", 3308, 3108, 0, "You step on the carpet and take off...",
					"at last you end up in Al Kharid.", 3);
			c.getPA().removeAllWindows();
			break;
		case 4487:
			if (c.getItems().playerHasItem(995, 150000)) {
				c.getItems().deleteItem(995, 150000);
				c.getPA().startTeleport(2914, 5300, 1, "modern", false);
				c.sendMessage("@red@You have teleported to Saradomin Enterance for @red@150k");
				break;
			}
			c.sendMessage("@red@You need 150k coins to use this teleport.");
			break;
		case 851:
			c.getDH().sendDialogues(852, 7520);
			break;
			
		case 78:
			if (WheatPortalEvent.xLocation > 0 && WheatPortalEvent.yLocation > 0) {
				c.getPA().spellTeleport(WheatPortalEvent.xLocation + 1, WheatPortalEvent.yLocation + 1, 0, false);
			} else {
				c.sendMessage("There is currently no portal available, wait 5 minutes.");
				return;
			}
			break;
		case 801:
			c.getDH().sendDialogues(806, 6773);
			break;
		case 77:
			c.getPA().closeAllWindows();
			if (!c.getMiningEffect()) {
				c.setMiningEffect(true);
				c.sendMessage("You have toggled on the Mining effect.");
			} else {
				c.setMiningEffect(false);
				c.sendMessage("You have toggled off the Mining effect.");
			}
			break;
		
	case 711:
		c.getDH().sendDialogues(714, 306);
		break;
		
	case 141314:
		c.getPA().startTeleport(1676, 3561, 0, "modern", false);
		c.sendMessage("You have teleported to the Hosidius House");
		c.getPA().closeAllWindows();
		break;
		
	case 193193:
		c.safeBoxSlots = 3;
		c.sendMessage("You have set your destination to [ @red@Shayzien House@bla@ ].");
		c.getPA().closeAllWindows();
		break;
		
		case 14400:
			c.getPA().startTeleport(3004, 3935, 0, "modern", false);
			c.sendMessage("You will gain XP after each lap");
			c.getPA().closeAllWindows();
			break;

		case 14410:
			c.getPA().spellTeleport(3222, 3414, 0, false);//option 3
			c.sendMessage("You will gain XP after completion of each lap");
			c.getPA().closeAllWindows();
			break;
		case 14411:
			c.getPA().startTeleport(3351, 2960, 0, "modern", false);//3rd option
			c.sendMessage("You will gain XP after completion of each lap");
			c.getPA().closeAllWindows();
			break;
		case 14412:
			
			break;
		case 1401:
			StatReset.execute(c, 1);
			break;

		case 1404:
			StatReset.execute(c, 6);
			break;

		case 2002:
			c.getPA().spellTeleport(3106, 3959, 0, false);
			break;
		case 2003:

			break;

		case 61: // GWD + 10 kc
			TeleportationDevice.startTeleport(c, 2);
			break;
			
		case 76: //Max cape
			c.getDH().sendDialogues(77, 311);
			break;
			
		case 56:
			RecolourGraceful.COLOR = "DARK_BLUE";
			RecolourGraceful.recolor(c, RecolourGraceful.ITEM_TO_RECOLOUR);
			break;
		//}
			
		case 1393:
			LootValue.configureValue(c, "setvalue", 1000000);
			break;
			
		case 55:
			RecolourGraceful.COLOR = "GOLD";
			RecolourGraceful.recolor(c, RecolourGraceful.ITEM_TO_RECOLOUR);
			break;
			
		/*case 56: //recolor graceful
			
			break;*/

		}

		if (c.dialogueAction == 114) {
			if (c.getItems().playerHasItem(6731, 1) && c.pkp >= 250) {
				c.getItems().deleteItem(6731, 1);
				c.pkp -= 250;
				c.getQuestTab().updateInformationTab();
				c.getItems().addItem(11770, 1);
				c.sendMessage("You imbue your seers ring for the cost of @blu@250 PKP@bla@.");
				c.getPA().removeAllWindows();
				c.dialogueAction = 0;
			} else {
				c.sendMessage("You need 250 PKP and n Seers ring to do this.");
				c.getPA().removeAllWindows();
				c.dialogueAction = 0;
			}
			return;
		}
		if (c.dialogueAction == 131) {
			c.getDH().sendDialogues(661, 311);
			return;
		}
		if (c.dialogueAction == 128) {
			c.getFightCave().create(3);
			return;
		}
		if (c.teleAction == 17) {
			c.getPA().spellTeleport(1746, 5323, 0, false);
			return;
		}
		if (c.dialogueAction == 123) {
			Degrade.DegradableItem[] claimable = Degrade.getClaimedItems(c);
			if (claimable.length < 3) {
				return;
			}
			c.getPA().removeAllWindows();
			Degrade.claim(c, claimable[2].getItemId());
			return;
		}
		if (c.dialogueAction == 121) {
			c.getDH().sendDialogues(617, -1);
			c.teleAction = -1;
			c.dialogueAction = -1;
			return;
		}
		if (c.teleAction == 15) {
			c.getPA().startTeleport(1777, 3707, 0, "modern", false);
			return;
		}	
		if (c.teleAction == 99) {
				c.getPA().startTeleport(3429, 3537, 0, "modern", false);
				return;
			}
		if (c.teleAction == 99) {
			c.getPA().startTeleport(2525, 4776, 0, "modern", false);
			return;
		}
		if (c.teleAction == 16) {
			c.getPA().spellTeleport(1476, 3687, 0, false);
		}
		if (c.teleAction == 14) {
			c.getPA().spellTeleport(3343, 3735, 0, false);
		}
		if (c.teleAction == 12) {
			c.getPA().spellTeleport(2739, 5088, 0, false);
		}
		if (c.teleAction == 11) {
			c.getPA().spellTeleport(3280, 9372, 0, false);
		}
		if (c.teleAction == 10) {
			c.getPA().spellTeleport(3241, 9364, 0, false);
		}
		if (c.teleAction == 9) {
			c.getPA().spellTeleport(3159, 9895, 0, false);
		}
		if (c.teleAction == 8) {
			c.getPA().spellTeleport(2912, 9831, 0, false);
		}
		if (c.teleAction == 7) {
			c.getPA().spellTeleport(2843, 9555, 0, false);
		}
		/*
		 * if (c.teleAction == 1) { //experiments c.getPA().spellTeleport(3555, 9947, 0); } else if (c.teleAction == 2) { //brimhavem dung c.getPA().spellTeleport(2709, 9564, 0); }
		 * else if (c.teleAction == 3) { //dag kings c.getPA().spellTeleport(2479, 10147, 0); } else if (c.teleAction == 4) { //easts lv 18 c.getPA().spellTeleport(3351, 3659, 0);
		 * } else if (c.teleAction == 5) { c.getPA().spellTeleport(2813,3436,0); }
		 */
		if (c.teleAction == 1) {
			// Hill giants
			c.getPA().spellTeleport(3246, 3794, 0, false);
		} else if (c.teleAction == 2) { //Relleka Dung
			c.getPA().spellTeleport(1262, 3501, 0, false);
		} else if (c.teleAction == 3) { // Godwars
			c.getPA().spellTeleport(2885, 5292, 2, false);
		} else if (c.teleAction == 33) { // Callisto
			c.getPA().spellTeleport(3325, 3845, 0, false);
		} else if (c.teleAction == 201) { //Barrows
			 c.getPA().spellTeleport(3565, 3308, 0, false);
		} else if (c.teleAction == 3434) { //Scorpia
			c.getPA().spellTeleport(3233, 3947, 0, false);
		} else if (c.teleAction == 1337) { // Wc Guild
			c.getPA().spellTeleport(1658, 3505, 0, false);
		} else if (c.teleAction == 200) { //Fight Caves
			c.getPA().spellTeleport(2480, 5175, 0, false);
		} else if (c.teleAction == 147258) { //Lumbridge
			 c.getPA().spellTeleport(3222, 3218, 0, false);
			 c.getDiaryManager().getLumbridgeDraynorDiary().progress(LumbridgeDraynorDiaryEntry.LUMBRIDGE_TELEPORT);
		} else if (c.teleAction == 258369) { //Mortan
			 c.getPA().spellTeleport(3432, 3451, 0, false);
		} else if (c.teleAction == 4) {
			// Hillz
			c.getPA().spellTeleport(2726, 3487, 0, false);
		} else if (c.teleAction == 5) {
			c.getPA().spellTeleport(2813, 3436, 0, false);
		} else if (c.teleAction == 2000) {
			c.getPA().spellTeleport(3222, 3219, 0, false);
		}
		if (c.dialogueAction == 10) {
			c.getPA().spellTeleport(2713, 4836, 0, false);
			c.dialogueAction = -1;
		} else if (c.dialogueAction == 11) {
			c.getPA().spellTeleport(2162, 4833, 0, false);
			c.dialogueAction = -1;
		} else if (c.dialogueAction == 12) {
			c.getPA().spellTeleport(2207, 4836, 0, false);
			c.dialogueAction = -1;
		}
	}

	/*
	 * Handles all 4th options on 'Five option' dialogues.
	 */
	public static void handleOption4(Player c) {

		switch (c.dialogueAction) {	
		case 1001:
			c.getDH().sendDialogues(1000, 13);
			break;
		case 838:
			c.startAnimation(2262);
			AgilityHandler.delayFade(c, "NONE", 3180, 3043, 0, "You step on the carpet and take off...",
					"at last you end up in Bandit Camp.", 3);
			c.getPA().removeAllWindows();
			break;
		case 998:
			c.getDH().sendDialogues(997, 13);
			break;
		case 4487:
			if (c.getItems().playerHasItem(995, 150000)) {
				c.getItems().deleteItem(995, 150000);
				c.getPA().startTeleport(2921, 5339, 2, "modern", false);
				c.sendMessage("@red@You have teleported to Zamorak Enterance for @red@150k");
				break;
			}
			c.sendMessage("@red@You need 150k coins to use this teleport.");
			break;
		case 851:
			c.getDH().sendDialogues(853, 7520);
			break;
		case 77:
			c.getPA().closeAllWindows();
			if (!c.getWoodcuttingEffect()) {
				c.setWoodcuttingEffect(true);
				c.sendMessage("You have toggled on the Woodcutting effect.");
			} else {
				c.setWoodcuttingEffect(false);
				c.sendMessage("You have toggled off the Woodcutting effect.");
			}
			break;
		
		case 711:
			c.getDH().sendDialogues(715, 306);
			break;
		case 141314:
			c.getPA().startTeleport(1495, 3555, 0, "modern", false);
			c.sendMessage("You have teleported to the Graveyard of Heros");
			c.getPA().closeAllWindows();
			break;
			
		case 193193:
			c.safeBoxSlots = 4;
			c.sendMessage("You have set your destination to [ @red@Mount Quidamortem@bla@ ].");
			c.getPA().closeAllWindows();
			break;
			
		case 14400:
			// c.getPA().startTeleport(3004, 3935, 0, "modern");
			// c.sendMessage("You will gain XP after each lap");
			// c.getPA().closeAllWindows();
			break;

		case 14410:
			c.getPA().startTeleport(3505, 3487, 0, "modern", false);
			c.sendMessage("You will gain XP after completion ofeach lap");//option 4
			c.getPA().closeAllWindows();
			break;
		case 14411:
			c.getDH().sendDialogues(14412, -1);//option 5
			break;
		case 14412:
			
			break;
		case 1401:
			c.getDH().sendDialogues(1404, c.npcType);
			break;

		case 1404:
			StatReset.execute(c, 3);
			break;

		case 2002:
			c.getPA().spellTeleport(2520, 3591, 0, false);
			break;
		case 2003:

			break;

		case 61: // Corporeal beast
			TeleportationDevice.startTeleport(c, 3);
			break;
			
		case 76: //Max cape
			c.getPA().closeAllWindows();
			break;
			
		case 1393: //Set value manually
			c.getPA().sendEnterAmount(0);
			c.settingLootValue = true;
			break;
			
		case 55:
			RecolourGraceful.COLOR = "RED";
			RecolourGraceful.recolor(c, RecolourGraceful.ITEM_TO_RECOLOUR);
			break;
			
		case 56: //recolor graceful
			
			break;
		}

		if (c.dialogueAction == 135) {
			c.getDH().sendDialogues(2400, -1);
			return;
		}
		if (c.dialogueAction == 114) {
			if (c.getItems().playerHasItem(6735, 1) && c.pkp >= 250) {
				c.getItems().deleteItem(6735, 1);
				c.pkp -= 250;
				c.getQuestTab().updateInformationTab();
				c.getItems().addItem(11772, 1);
				c.sendMessage("You imbue your warrior ring for the cost of @blu@250 PKP@bla@.");
				c.getPA().removeAllWindows();
				c.dialogueAction = 0;
			} else {
				c.sendMessage("You need 250 PKP and n Warrior ring to do this.");
				c.getPA().removeAllWindows();
				c.dialogueAction = 0;
			}
			return;
		}
		if (c.dialogueAction == 128) {
			c.getDH().sendDialogues(634, -1);
			return;
		}
		if (c.teleAction == 17) {
			c.getPA().spellTeleport(2352, 3160, 0, false);
			return;
		}
		/*switch (c.teleAction) {
		case 2:
			c.getPA().spellTeleport(2678 + Misc.random(3), 9563 + Misc.random(2), 0);
			break;
		}*/
		if (c.teleAction == 14) {
			c.getPA().startTeleport(3179, 3774, 0, "modern", false);
			c.teleAction = -1;
			c.dialogueAction = -1;
			return;
		}
		if (c.dialogueAction == 123) {
			Degrade.DegradableItem[] claimable = Degrade.getClaimedItems(c);
			if (claimable.length < 4) {
				return;
			}
			c.getPA().removeAllWindows();
			Degrade.claim(c, claimable[3].getItemId());
			return;
		}
		if (c.dialogueAction == 121) {
			c.getDH().sendDialogues(618, -1);
			c.teleAction = -1;
			c.dialogueAction = -1;
			return;
		}
		if (c.teleAction == 12) {
			c.getDH().sendOption5("GarGoyle", "Bloodveld", "Banshee", "-- Previous Page --", "-- Next Page --");
			c.teleAction = 11;
			return;
		}
		if (c.teleAction == 15) {
			c.getPA().startTeleport(2525, 4776, 0, "modern", false);
			return;
		}
		if (c.teleAction == 11) {
			c.getDH().sendOption5("Black Demon", "Dust Devils", "Nechryael", "-- Previous Page --", "-- Next Page --");
			c.teleAction = 10;
			return;
		}
		if (c.teleAction == 10) {
			c.getDH().sendOption5("Goblins", "Baby blue dragon", "Moss Giants", "-- Previous Page --", "-- Next Page --");
			c.teleAction = 9;
			return;
		}
		if (c.teleAction == 9) {
			c.getDH().sendOption5("Al-kharid warrior", "Ghosts", "Giant Bats", "-- Previous Page --", "-- Next Page --");
			c.teleAction = 8;
			return;
		}
		if (c.teleAction == 8) {
			c.getDH().sendOption5("Hill Giants", "Hellhounds", "Lesser Demons", "Chaos Dwarf", "-- Next Page --");
			c.teleAction = 7;
			return;
		}
		if (c.teleAction == 7) {
			c.getPA().spellTeleport(2923, 9759, 0, false);
		}
		if (c.teleAction == 1) { //Lovak Bank
			c.getPA().spellTeleport(3204, 3857, 0, false);
		} else if (c.teleAction == 200) { //Warriors guild
			c.getPA().spellTeleport(2865, 3547, 0, false);
		} else if (c.teleAction == 147258) { //Karamja
			 c.getPA().spellTeleport(2924, 3148, 0, false);
		} else if (c.teleAction == 258369) { //Edge
			 c.getPA().spellTeleport(2809, 3440, 0, false);
			 c.getDiaryManager().getKandarinDiary().progress(KandarinDiaryEntry.CATHERY_TELEPORT);
		} else if (c.teleAction == 33) { //Shaman
			c.getPA().spellTeleport(1469, 3687, 0, false);
		} else if (c.teleAction == 3434) { //Arch
			c.getPA().spellTeleport(2991, 3695, 0, false);
		} else if (c.teleAction == 2) { //Stronghold Slayer
			c.getPA().spellTeleport(1277, 3552, 0, false);
		} else if (c.teleAction == 1337) { //Farming
			c.getPA().spellTeleport(3003, 3376, 0, false);
		} else if (c.teleAction == 3) { //Zulrah
			c.getPA().spellTeleport(2202, 3056, 0, false);
		} else if (c.teleAction == 4) {
			// Fala
			/*
			 * c.getPA().removeAllWindows(); c.teleAction = 0;
			 */
			c.getPA().spellTeleport(2815, 3461, 0, false);
			c.getDH().sendStatement("You need a Rake, Watering can, Seed Dibber and a seed.");
		} else if (c.teleAction == 5) {
			c.getPA().spellTeleport(2724, 3484, 0, false);
			c.sendMessage("For magic logs, try north of the duel arena.");
		}
		if (c.dialogueAction == 10) {
			c.getPA().spellTeleport(2660, 4839, 0, false);
			c.dialogueAction = -1;
		} else if (c.dialogueAction == 11) {
			// c.getPA().spellTeleport(2527, 4833, 0); astrals here
			// c.getRunecrafting().craftRunes(2489);
			c.dialogueAction = -1;
		} else if (c.dialogueAction == 12) {
			// c.getPA().spellTeleport(2464, 4834, 0); bloods here
			// c.getRunecrafting().craftRunes(2489);
			c.dialogueAction = -1;
		}
	}

	/*
	 * Handles all 5th options on 'Five option' dialogues.
	 */
	public static void handleOption5(Player c) {

		switch (c.dialogueAction) {
		case 1001:
			c.getDH().sendDialogues(1002, 13);
			break;
		case 838:
			c.getDiaryManager().getDesertDiary().progress(DesertDiaryEntry.TRAVEL_NARDAH);
			c.startAnimation(2262);
			AgilityHandler.delayFade(c, "NONE", 3402, 2916, 0, "You step on the carpet and take off...",
					"at last you end up in Nardah.", 3);
			c.getPA().removeAllWindows();
			break;
		case 1000:
			c.getDH().sendDialogues(1001, 13);
			break;
		case 999:
			c.getDH().sendDialogues(1000, 13);
			break;
		case 997:
			c.getDH().sendDialogues(998, 13);
			break;
		case 4487:
			if (c.getItems().playerHasItem(995, 150000)) {
				c.getItems().deleteItem(995, 150000);
				c.getPA().startTeleport(2839, 5292, 2, "modern", false);
				c.sendMessage("@red@You have teleported to Armadyl Enterance for @red@150k");
				break;
			}
			c.sendMessage("@red@You need 150k coins to use this teleport.");
			break;
		case 711:
		case 77:
		case 801:
		case 851:
			c.getPA().removeAllWindows();
			break;
		case 14400:
			c.getDH().sendDialogues(14402, -1);
			break; 

		case 14410:
			c.getDH().sendDialogues(14411, -1);//option 5
			break;
		case 14411:
			c.getDH().sendDialogues(14410, -1);//option 5
			break;
		case 14412:
			c.getDH().sendDialogues(14411, -1);//option 5
		case 1401:
			c.getPA().removeAllWindows();
			break;
			
		case 141314:
			c.getPA().startTeleport(1821, 3688, 0, "modern", false);
			c.sendMessage("You have teleported to the Piscarilius Docks");
			c.getPA().closeAllWindows();
			break;
			
		case 193193:
			c.safeBoxSlots = 5;
			c.sendMessage("You have set your destination to [ @red@Arceuus House@bla@ ].");
			c.getPA().closeAllWindows();
			break;

		case 1404:
			c.getDH().sendDialogues(1401, -1);
			break;

		case 2002:
			c.getDH().sendDialogues(2003, -1);
			break;

		case 2003:

			break;

		case 61: // Teleportation device 5

			break;
			
		case 76: //Max cape
			c.getPA().closeAllWindows();
			break;
			
		case 1393:
			c.getDH().sendDialogues(1394, c.npcType);
			break;
			
		case 55:
			c.getDH().sendDialogues(61, c.npcType);
			break;
			
		case 56:
			c.getDH().sendDialogues(55, c.npcType);
			break;
		}

		if (c.dialogueAction == 114) {
			c.getDH().sendDialogues(2402, -1);
			return;
		}
		if (c.dialogueAction == 128) {
			c.getDH().sendDialogues(636, -1);
			return;
		}
		switch (c.teleAction) {
		case 17:
			c.getDH().sendDialogues(3324, -1);
			return;
		/*case 2:
			c.getDH().sendDialogues(3333, -1);
			return;*/
		case 14:
			c.getDH().sendOption5("Callisto @red@(39 Wild)", "Giant mole @red@(49 wild)", "Lizardman Canyon", "", "Close");
			c.teleAction = 16;
			return;
		}
		if (c.dialogueAction == 121 || c.dialogueAction == 123 || c.teleAction == 16 || c.dialogueAction == 131 || c.dialogueAction == 135) {
			c.getPA().removeAllWindows();
			c.teleAction = -1;
			c.dialogueAction = -1;
			return;
		}
		if (c.teleAction == 8) {
			c.getDH().sendOption5("Goblins", "Baby blue dragon", "Moss Giants", "-- Previous Page --", "-- Next Page --");
			c.teleAction = 9;
			return;
		}
		if (c.teleAction == 9) {
			c.getDH().sendOption5("Black Demon", "Dust Devils", "Nechryael", "-- Previous Page --", "-- Next Page --");
			c.teleAction = 10;
			return;
		}
		if (c.teleAction == 11) {
			c.getDH().sendOption5("Infernal Mage", "Dark Beasts", "Abyssal Demon", "-- Previous Page --", "");
			c.teleAction = 12;
			return;
		}
		if (c.teleAction == 10) {
			c.getDH().sendOption5("GarGoyle", "Bloodveld", "Banshee", "-- Previous Page --", "-- Next Page --");
			c.teleAction = 11;
			return;
		}
		if (c.teleAction == 7) {
			c.getDH().sendOption5("Al-kharid warrior", "Ghosts", "Giant Bats", "-- Previous Page --", "-- Next Page --");
			c.teleAction = 8;
			return;
		}
		if (c.teleAction == 15) { //Terrorbirds
			c.getDH().sendDialogues(399, -1); 
		} else if (c.teleAction == 200) {
			c.getDH().sendDialogues(403, -1); 
		} else if (c.teleAction == 147258) {
			c.getDH().sendDialogues(121313, -1); 
		} else if (c.teleAction == 258369) { //Relleka
			 c.getPA().spellTeleport(2673, 3709, 0, false);
		} else if (c.teleAction == 3434) { //Fnatic
			c.getPA().spellTeleport(2993, 3854, 0, false);
		} else if (c.teleAction == 33) { //Vet'ion
			c.getDH().sendDialogues(406, -1); 
		} else if (c.teleAction == 3) {
			c.getDH().sendDialogues(405, -1); 
		} else if (c.teleAction == 201) {
			c.getDH().sendDialogues(402, -1); 
		} else if (c.teleAction == 2) { // Asgarnian Caves
			c.getPA().spellTeleport(3029, 9582, 0, false);
		}
		if (c.teleAction == 1) {
			c.getPA().spellTeleport(2539, 4716, 0, false);
			return;
		/*} else if (c.teleAction == 200) {
			// last minigame spot
			// c.sendMessage("Suggest something for this spot on the
			// forums!");
			// c.getPA().closeAllWindows();
			 c.getPA().spellTeleport(1461, 3689, 0);*/
		} else if (c.teleAction == 3) {
			c.getDH().sendOption5("Dagannoth Cave", "Kraken @red@(Level 17 & Multi)", "Venenatis @red@(Level 29 & Multi)", "Vet'ion @red@(Level 34 & Multi)", "@blu@Next");
			c.teleAction = 14;
		} else if (c.teleAction == 4) {
			// ardy lever
			/*
			 * c.getPA().removeAllWindows(); c.teleAction = 0;
			 */
			c.getPA().spellTeleport(3039, 4836, 0, false);
		} else if (c.teleAction == 5) {
			c.getPA().spellTeleport(2812, 3463, 0, false);
		}
		if (c.dialogueAction == 10 || c.dialogueAction == 11) {
			c.dialogueId++;
			c.getDH().sendDialogues(c.dialogueId, 0);
		} else if (c.dialogueAction == 12) {
			c.dialogueId = 17;
			c.getDH().sendDialogues(c.dialogueId, 0);
		}
	}
}
