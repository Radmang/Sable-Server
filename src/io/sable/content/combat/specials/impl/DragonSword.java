package io.sable.content.combat.specials.impl;

import io.sable.content.combat.Damage;
import io.sable.content.combat.melee.CombatPrayer;
import io.sable.content.combat.specials.Special;
import io.sable.model.entity.Entity;
import io.sable.model.entity.player.Player;

public class DragonSword extends Special {

	public DragonSword() {
		super(4, 1.25, 1.25, new int[] { 21009 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		player.gfx100(1369);
		player.startAnimation(7515);
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {
		if (target instanceof Player) {
			if (damage.getAmount() > 0) {
				CombatPrayer.resetOverHeads((Player) target);
			}
		}

	}

}
