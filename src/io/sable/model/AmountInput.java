package io.sable.model;

import io.sable.model.entity.player.Player;

public interface AmountInput {
    void handle(Player player, int amount);
}
