package io.sable.content.combat.core;


import io.sable.content.combat.Damage;
import io.sable.content.combat.melee.MeleeExtras;
import io.sable.model.entity.Entity;
import io.sable.model.entity.player.Player;

public class HitExecutorPlayer extends HitExecutor {

    public HitExecutorPlayer(Player c, Entity defender, Damage damage) {
        super(c, defender, damage);
    }

    @Override
    public void onHit() {
        Player o = defender.asPlayer();
        o.addDamageTaken(attacker, damage.getAmount());
        o.logoutDelay = System.currentTimeMillis();
        o.underAttackByPlayer = attacker.getIndex();
        o.killerId = attacker.getIndex();
        o.singleCombatDelay = System.currentTimeMillis();
        /**
         * Upon receiving damage
         */
        MeleeExtras.applyOnHit(attacker, o, damage);
    }
}
