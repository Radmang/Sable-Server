package io.sable.content.worldevent.impl;

import java.util.List;

import io.sable.content.commands.Command;
import io.sable.content.commands.all.Outlast;
import io.sable.content.tournaments.TourneyManager;
import io.sable.content.worldevent.WorldEvent;
import io.sable.model.entity.player.Player;
import io.sable.model.entity.player.Position;
import io.sable.model.entity.player.broadcasts.Broadcast;

public class TournamentWorldEvent implements WorldEvent {

    private final TourneyManager tourney = TourneyManager.getSingleton();

    @Override
    public void init() {
        tourney.openLobby();
    }

    @Override
    public void dispose() {
        tourney.endGame();
    }

    @Override
    public boolean isEventCompleted() {
        return !tourney.isLobbyOpen() && !tourney.isArenaActive();
    }

    @Override
    public String getCurrentStatus() {
        return tourney.getTimeLeft();
    }

    @Override
    public String getEventName() {
        return "Outlast";
    }

    @Override
    public String getStartDescription() {
        return "starts";
    }

    @Override
    public Class<? extends Command> getTeleportCommand() {
        return Outlast.class;
    }

    @Override
    public void announce(List<Player> players) {
        new Broadcast("An Outlast " + tourney.getTournamentType() + " tournament will begin soon, type ::outlast or click HERE to join!").addTeleport(new Position(3080, 3510, 0)).copyMessageToChatbox().submit();
    }
}
