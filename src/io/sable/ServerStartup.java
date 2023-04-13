package io.sable;

import io.sable.annotate.Init;
import io.sable.annotate.PostInit;
import io.sable.content.boosts.Boosts;
import io.sable.content.bosses.godwars.GodwarsEquipment;
import io.sable.content.bosses.godwars.GodwarsNPCs;
import io.sable.content.bosses.nightmare.NightmareStatusNPC;
import io.sable.content.bosses.sarachnis.SarachnisNpc;
import io.sable.content.collection_log.CollectionLog;
import io.sable.content.combat.stats.TrackedMonster;
import io.sable.content.commands.CommandManager;
import io.sable.content.dailyrewards.DailyRewardContainer;
import io.sable.content.dailyrewards.DailyRewardsRecords;
import io.sable.content.donationrewards.DonationReward;
import io.sable.content.event.eventcalendar.EventCalendar;
import io.sable.content.event.eventcalendar.EventCalendarWinnerSelect;
import io.sable.content.events.monsterhunt.MonsterHunt;
import io.sable.content.fireofexchange.FireOfExchangeBurnPrice;
import io.sable.content.polls.PollTab;
import io.sable.content.preset.PresetManager;
import io.sable.content.referral.ReferralCode;
import io.sable.content.skills.runecrafting.ouriana.ZamorakGuardian;
import io.sable.content.tournaments.TourneyManager;
import io.sable.content.tradingpost.Listing;
import io.sable.content.trails.TreasureTrailsRewards;
import io.sable.content.vote_panel.VotePanelManager;
import io.sable.content.wogw.Wogw;
import io.sable.content.worldevent.WorldEventContainer;
import io.sable.model.Npcs;
import io.sable.model.collisionmap.ObjectDef;
import io.sable.model.collisionmap.Region;
import io.sable.model.collisionmap.doors.DoorDefinition;
import io.sable.model.cycleevent.impl.BonusApplianceEvent;
import io.sable.model.cycleevent.impl.DidYouKnowEvent;
import io.sable.model.cycleevent.impl.LeaderboardUpdateEvent;
import io.sable.model.cycleevent.impl.UpdateQuestTab;
import io.sable.model.definitions.AnimationLength;
import io.sable.model.definitions.ItemDef;
import io.sable.model.definitions.ItemStats;
import io.sable.model.definitions.NpcDef;
import io.sable.model.definitions.NpcStats;
import io.sable.model.definitions.ShopDef;
import io.sable.model.entity.npc.NPCRelationship;
import io.sable.model.entity.npc.NpcSpawnLoader;
import io.sable.model.entity.npc.stats.NpcCombatDefinition;
import io.sable.model.entity.player.PlayerFactory;
import io.sable.model.entity.player.save.PlayerSave;
import io.sable.model.lobby.LobbyManager;
import io.sable.model.world.ShopHandler;
import io.sable.punishments.PunishmentCycleEvent;
import io.sable.model.entity.player.save.backup.PlayerSaveBackup;
import io.sable.util.Reflection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Stuff to do on startup.
 * @author Michael Sasse (https://github.com/mikeysasse/)
 */
public class ServerStartup {

    private static final Logger logger = LoggerFactory.getLogger(ServerStartup.class);

    static void load() throws Exception {
        Reflection.getMethodsAnnotatedWith(Init.class).forEach(method -> {
            try {
                method.invoke(null);
            } catch (Exception e) {
                logger.error("Error loading @Init annotated method[{}] inside class[{}]", method, method.getClass(), e);
                e.printStackTrace();
                System.exit(1);
            }
        });

        DonationReward.load();
        PlayerSave.loadPlayerSaveEntries();
        EventCalendarWinnerSelect.getInstance().init();
        TrackedMonster.init();
        Boosts.init();
        ItemDef.load();
        ShopDef.load();
        ShopHandler.load();
        NpcStats.load();
        ItemStats.load();
        NpcDef.load();
        // Npc Combat Definition must be above npc load
        NpcCombatDefinition.load();
        Server.npcHandler.init();
        NPCRelationship.setup();
        EventCalendar.verifyCalendar();
        Server.getPunishments().initialize();
        Server.getEventHandler().submit(new DidYouKnowEvent());
        Server.getEventHandler().submit(new BonusApplianceEvent());
        Server.getEventHandler().submit(new PunishmentCycleEvent(Server.getPunishments(), 50));
        Server.getEventHandler().submit(new UpdateQuestTab());
        Server.getEventHandler().submit(new LeaderboardUpdateEvent());
        Listing.init();
        Wogw.init();
        PollTab.init();
        DoorDefinition.load();
        GodwarsEquipment.load();
        GodwarsNPCs.load();
        LobbyManager.initializeLobbies();
        VotePanelManager.init();
        TourneyManager.initialiseSingleton();
        TourneyManager.getSingleton().init();
        Server.getDropManager().read();
        TreasureTrailsRewards.load();
        AnimationLength.startup();
        PresetManager.getSingleton().init();
        ObjectDef.loadConfig();
        CollectionLog.init();
        Region.load();
        Server.getGlobalObjects().loadGlobalObjectFile();

        // Keep this below region load and object loading
        NpcSpawnLoader.load();
        MonsterHunt.spawnNPC();
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
        CommandManager.initializeCommands();
        NightmareStatusNPC.init();
        if (Server.isDebug()) {
            PlayerFactory.createTestPlayers();
        }
        ReferralCode.load();
        DailyRewardContainer.load();
        DailyRewardsRecords.load();
        WorldEventContainer.getInstance().initialise();
        FireOfExchangeBurnPrice.init();
        Server.getLogging().schedule();

        ZamorakGuardian.spawn();
        new SarachnisNpc(Npcs.SARACHNIS, SarachnisNpc.SPAWN_POSITION);

        if (Server.isPublic()) {
            PlayerSaveBackup.start(Configuration.PLAYER_SAVE_TIMER_MILLIS, Configuration.PLAYER_SAVE_BACKUP_EVERY_X_SAVE_TICKS);
        }

        Reflection.getMethodsAnnotatedWith(PostInit.class).forEach(method -> {
            try {
                method.invoke(null);
            } catch (Exception e) {
                logger.error("Error loading @PostInit annotated method[{}] inside class[{}]", method, method.getClass(), e);
                e.printStackTrace();
                System.exit(1);
            }
        });
    }

}
