package io.sable.content.boosts.other;

import io.sable.Configuration;
import io.sable.model.entity.player.Player;
import io.sable.util.Misc;

public class DoubleDropsBoost extends GenericBoost {
    @Override
    public String getDescription() {
        return "+100% Drop Rate (" + Misc.cyclesToDottedTime((int) Configuration.DOUBLE_DROPS_TIMER) + ")";
    }

    @Override
    public boolean applied(Player player) {
        return Configuration.DOUBLE_DROPS_TIMER > 0;
    }
}
