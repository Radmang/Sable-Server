package io.sable.content.boosts.other;

import io.sable.content.wogw.Wogw;
import io.sable.model.entity.player.Player;
import io.sable.util.Misc;

public class WogwDropBoost extends GenericBoost {
    @Override
    public String getDescription() {
        return "+20% Drop Rate (" + Misc.cyclesToDottedTime((int) Wogw._20_PERCENT_DROP_RATE_TIMER) + ")";
    }

    @Override
    public boolean applied(Player player) {
        return Wogw._20_PERCENT_DROP_RATE_TIMER > 0;
    }
}
