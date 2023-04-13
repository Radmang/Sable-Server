package io.sable.content.combat.effects.damageeffect;

import io.sable.model.entity.Entity;
import io.sable.model.entity.player.Player;

public interface DamageBoostingEffect extends DamageEffect {

    double getMaxHitBoost(Player attacker, Entity defender);

}
