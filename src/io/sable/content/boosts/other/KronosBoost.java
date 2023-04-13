package io.sable.content.boosts.other;

import io.sable.content.bosses.hespori.Hespori;
import io.sable.model.entity.player.Player;
import io.sable.util.Misc;

public class KronosBoost extends GenericBoost {
    @Override
    public String getDescription() {
        return "x2 Raids Keys (" + Misc.cyclesToDottedTime((int) Hespori.KRONOS_TIMER) + ")";
    }

    @Override
    public boolean applied(Player player) {
        return Hespori.KRONOS_TIMER > 0;
    }
}
