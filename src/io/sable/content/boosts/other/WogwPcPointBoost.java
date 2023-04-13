package io.sable.content.boosts.other;

import io.sable.content.wogw.Wogw;
import io.sable.model.entity.player.Player;
import io.sable.util.Misc;

public class WogwPcPointBoost extends GenericBoost {
    @Override
    public String getDescription() {
        return "+5 PC Points (" + Misc.cyclesToDottedTime((int) Wogw.PC_POINTS_TIMER) + ")";
    }

    @Override
    public boolean applied(Player player) {
        return Wogw.PC_POINTS_TIMER > 0;
    }
}
