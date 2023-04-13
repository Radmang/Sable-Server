package io.sable.content.combat.specials.impl;

import io.sable.content.combat.Damage;
import io.sable.content.combat.melee.CombatPrayer;
import io.sable.content.combat.specials.Special;
import io.sable.model.entity.Entity;
import io.sable.model.entity.player.Player;

public class DragonScimitar extends Special {

	public DragonScimitar() {
		super(5.5, 1.25, 1.00, new int[] { 4587 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		player.gfx100(347);
		player.startAnimation(1872);
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
