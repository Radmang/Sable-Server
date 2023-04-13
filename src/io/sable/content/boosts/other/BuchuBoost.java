package io.sable.content.boosts.other;

import io.sable.content.bosses.hespori.Hespori;
import io.sable.model.entity.player.Player;
import io.sable.util.Misc;

public class BuchuBoost extends GenericBoost {
    @Override
    public String getDescription() {
        return "x2 Boss Points (" + Misc.cyclesToDottedTime((int) Hespori.BUCHU_TIMER) + ")";
    }

    @Override
    public boolean applied(Player player) {
        return Hespori.BUCHU_TIMER > 0;
    }
}
