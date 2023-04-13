package io.sable.model.multiplayersession.flowerpoker;

import io.sable.model.Items;
import io.sable.model.entity.player.Player;
import io.sable.model.entity.player.lock.CompleteLock;

public class FlowerPokerLock extends CompleteLock {
    @Override
    public boolean cannotClickItem(Player player, int itemId) {
        if (itemId == Items.MITHRIL_SEEDS)
            return false;
        return true;
    }
}
