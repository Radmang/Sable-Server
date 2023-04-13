package io.sable.model.entity.player.lock;

import io.sable.model.entity.player.Player;

public class Unlocked implements PlayerLock {
    @Override
    public boolean cannotLogout(Player player) {
        return false;
    }

    @Override
    public boolean cannotInteract(Player player) {
        return false;
    }

    @Override
    public boolean cannotClickItem(Player player, int itemId) {
        return false;
    }

    @Override
    public boolean cannotTeleport(Player player) {
        return false;
    }
}
