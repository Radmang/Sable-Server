package io.sable.content.combat.effects.special;

import io.sable.model.entity.player.Player;

/**
 * @author Arthur Behesnilian 9:09 PM
 */
public interface SpecialEffect {

    boolean activateSpecialEffect(Player player, Object... args);

}
