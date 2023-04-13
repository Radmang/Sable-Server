package io.sable.util.logging.player;

import io.sable.content.achievement.Achievements;
import io.sable.model.entity.player.Player;
import io.sable.util.Misc;
import io.sable.util.logging.PlayerLog;

import java.util.Set;

public class ClaimAchievementLog extends PlayerLog {

    private final Achievements.Achievement achievement;

    public ClaimAchievementLog(Player player, Achievements.Achievement achievement) {
        super(player);
        this.achievement = achievement;
    }

    @Override
    public Set<String> getLogFileNames() {
        return Set.of("claimed_achievement");
    }

    @Override
    public String getLoggedMessage() {
        return Misc.replaceBracketsWithArguments("Claimed achievement {}", achievement);
    }
}
