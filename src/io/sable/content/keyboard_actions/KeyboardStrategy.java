package io.sable.content.keyboard_actions;

import io.sable.model.entity.player.Player;

@FunctionalInterface
public interface KeyboardStrategy {
    void execute(Player player);
}
