package io.sable.util.logging.player;

import io.sable.model.entity.player.Player;
import io.sable.model.items.GameItem;
import io.sable.util.logging.PlayerLog;

import java.util.Set;

public class LeaderboardRewardCollected extends PlayerLog {

    private final GameItem gameItem;

    public LeaderboardRewardCollected(Player player, GameItem gameItem) {
        super(player);
        this.gameItem = gameItem;
    }

    @Override
    public Set<String> getLogFileNames() {
        return Set.of("leaderboard_rewards");
    }

    @Override
    public String getLoggedMessage() {
        return "Collected " + gameItem;
    }
}
