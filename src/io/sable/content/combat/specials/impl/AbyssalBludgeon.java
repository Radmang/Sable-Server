package io.sable.content.combat.specials.impl;

import io.sable.content.combat.Damage;
import io.sable.content.combat.specials.Special;
import io.sable.model.entity.Entity;
import io.sable.model.entity.npc.NPC;
import io.sable.model.entity.player.Player;

public class AbyssalBludgeon extends Special {

	public AbyssalBludgeon() {
		super(5.0, 2.10, 1.30, new int[] { 13263 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		player.startAnimation(3299);
		if (target instanceof Player) {
			((Player) target).gfx0(1284);
		}
		if (target instanceof NPC) {
			((NPC) target).gfx0(1284);
		}
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {

	}

}
