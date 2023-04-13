package io.sable.content.boosts.other;

import io.sable.content.boosts.BoostType;
import io.sable.content.boosts.Booster;
import io.sable.model.entity.player.Player;

public abstract class GenericBoost implements Booster<Player> {
    @Override
    public BoostType getType() {
        return BoostType.GENERIC;
    }
}
