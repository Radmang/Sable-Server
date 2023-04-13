package io.sable.content.boosts.other;

import io.sable.content.bosses.hespori.Hespori;
import io.sable.model.entity.player.Player;
import io.sable.util.Misc;

public class CelastrusBoost extends GenericBoost {
    @Override
    public String getDescription() {
        return "x2 Brimstone Keys (" + Misc.cyclesToDottedTime((int) Hespori.CELASTRUS_TIMER) + ")";
    }

    @Override
    public boolean applied(Player player) {
        return Hespori.CELASTRUS_TIMER > 0;
    }
}
