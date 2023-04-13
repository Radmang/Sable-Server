package io.sable.content.combat.specials.impl;

import io.sable.content.combat.Damage;
import io.sable.content.combat.core.HitDispatcher;
import io.sable.content.combat.specials.Special;
import io.sable.model.CombatType;
import io.sable.model.entity.Entity;
import io.sable.model.entity.npc.NPC;
import io.sable.model.entity.player.Player;

public class DragonHalberd extends Special {

	public DragonHalberd() {
		super(3.0, 1.00, 1.10, new int[] { 3204 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		player.gfx100(478);
		player.startAnimation(1203);
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {
		if (target instanceof NPC) {
			NPC other = (NPC) target;
			if (other != null && player.npcAttackingIndex > 0) {
				if (player.goodDistance(player.getX(), player.getY(), other.getX(), other.getY(), other.getSize()) && other.getSize() > 1) {
					HitDispatcher.getHitEntity(player, target).playerHitEntity(CombatType.MELEE, null);
				}
			}
		}
	}

}
