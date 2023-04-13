package io.sable.content.boosts.xp;

import io.sable.content.boosts.BoostType;
import io.sable.content.boosts.Booster;
import io.sable.content.boosts.PlayerSkillWrapper;

public abstract class ExperienceBooster implements Booster<PlayerSkillWrapper> {

    @Override
    public BoostType getType() {
        return BoostType.EXPERIENCE;
    }

}
