package io.sable.content.boosts.other;

import io.sable.content.bosses.hespori.Hespori;
import io.sable.model.entity.player.Player;
import io.sable.util.Misc;

public class NoxiferBoost extends GenericBoost {
    @Override
    public String getDescription() {
        return "x2 Slayer Points (" + Misc.cyclesToDottedTime((int) Hespori.NOXIFER_TIMER) + ")";
    }

    @Override
    public boolean applied(Player player) {
        return Hespori.NOXIFER_TIMER > 0;
    }
}
