package io.sable.util.logging.player;

import io.sable.model.entity.player.Player;
import io.sable.util.logging.PlayerLog;

import java.util.Set;

public class DeathLog extends PlayerLog {

    private final Player player;

    public DeathLog(Player player) {
        super(player);
        this.player = player;
    }

    @Override
    public Set<String> getLogFileNames() {
        return Set.of("death");
    }

    @Override
    public String getLoggedMessage() {
        return player.getPosition().toString();
    }
}
