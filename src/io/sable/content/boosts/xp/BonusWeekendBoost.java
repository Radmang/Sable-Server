package io.sable.content.boosts.xp;

import io.sable.content.bonus.DoubleExperience;
import io.sable.content.boosts.PlayerSkillWrapper;

public class BonusWeekendBoost extends ExperienceBooster {
    @Override
    public String getDescription() {
        return "+50% XP Bonus Weekend";
    }

    @Override
    public boolean applied(PlayerSkillWrapper playerSkillWrapper) {
        return DoubleExperience.isDoubleExperience();
    }
}
