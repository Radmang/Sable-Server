package io.sable.content.worldevent.impl;

import java.util.List;

import io.sable.content.commands.Command;
import io.sable.content.commands.all.Wildyevent;
import io.sable.content.events.monsterhunt.MonsterHunt;
import io.sable.content.worldevent.WorldEvent;
import io.sable.model.entity.player.Player;
import io.sable.model.entity.player.Position;
import io.sable.model.entity.player.broadcasts.Broadcast;

public class WildernessBossWorldEvent implements WorldEvent {

    @Override
    public void init() {
        MonsterHunt.spawnNPC();
    }

    @Override
    public void dispose() {
        if (!isEventCompleted()) {
            MonsterHunt.despawn();
        }
    }

    @Override
    public boolean isEventCompleted() {
        return !MonsterHunt.spawned;
    }

    @Override
    public String getCurrentStatus() {
        return MonsterHunt.getTimeLeft();
    }

    @Override
    public String getEventName() {
        return "Wildy Boss";
    }

    @Override
    public String getStartDescription() {
        return "spawns";
    }

    @Override
    public Class<? extends Command> getTeleportCommand() {
        return Wildyevent.class;
    }

    @Override
    public void announce(List<Player> players) {
        new Broadcast(MonsterHunt.getName() + " has spawned at "
                + MonsterHunt.getCurrentLocation().getLocationName() + ", use ::wildyevent to teleport!").addTeleport(new Position(MonsterHunt.getCurrentLocation().getX(), MonsterHunt.getCurrentLocation().getY(), 0)).copyMessageToChatbox().submit();
    }
}
