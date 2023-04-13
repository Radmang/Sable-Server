package io.sable.content.boosts.xp;

import io.sable.content.boosts.PlayerSkillWrapper;
import io.sable.content.bosses.hespori.Hespori;
import io.sable.util.Misc;

public class AttasBoost extends ExperienceBooster {
    @Override
    public String getDescription() {
        return "+50% XP (" + Misc.cyclesToDottedTime((int) Hespori.ATTAS_TIMER) + ")";
    }

    @Override
    public boolean applied(PlayerSkillWrapper playerSkillWrapper) {
        return Hespori.ATTAS_TIMER > 0;
    }
}
