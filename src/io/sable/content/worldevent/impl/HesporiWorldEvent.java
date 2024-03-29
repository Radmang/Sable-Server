package io.sable.content.worldevent.impl;

import java.util.List;

import io.sable.content.bosses.hespori.Hespori;
import io.sable.content.bosses.hespori.HesporiSpawner;
import io.sable.content.commands.Command;
import io.sable.content.commands.all.Worldevent;
import io.sable.content.worldevent.WorldEvent;
import io.sable.model.entity.player.Player;
import io.sable.model.entity.player.Position;
import io.sable.model.entity.player.broadcasts.Broadcast;

public class HesporiWorldEvent implements WorldEvent {
    @Override
    public void init() {
        HesporiSpawner.spawnNPC();
    }

    @Override
    public void dispose() {
        if (!isEventCompleted()) {
            Hespori.rewardPlayers(false);
        }
    }

    @Override
    public boolean isEventCompleted() {
        return !HesporiSpawner.isSpawned();
    }

    @Override
    public String getCurrentStatus() {
        return "World Event: @gre@Hespori";
    }

    @Override
    public String getEventName() {
        return "Hespori";
    }

    @Override
    public String getStartDescription() {
        return "spawns";
    }

    @Override
    public Class<? extends Command> getTeleportCommand() {
        return Worldevent.class;
    }

    @Override
    public void announce(List<Player> players) {
        new Broadcast("Hespori world boss has spawned, use ::worldevent to fight!").addTeleport(new Position(3072, 3499, 0)).copyMessageToChatbox().submit();
    }
}
