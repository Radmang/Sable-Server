package io.sable.content.combat.effects.damageeffect.impl;

import java.util.Optional;

import io.sable.content.combat.Damage;
import io.sable.content.combat.effects.damageeffect.DamageEffect;
import io.sable.model.entity.HealthStatus;
import io.sable.model.entity.npc.NPC;
import io.sable.model.entity.player.Player;
import io.sable.util.Misc;

public class ToxicBlowpipeEffect implements DamageEffect {

	@Override
	public void execute(Player attacker, Player defender, Damage damage) {
		defender.getHealth().proposeStatus(HealthStatus.VENOM, 6, Optional.of(attacker));
	}

	@Override
	public void execute(Player attacker, NPC defender, Damage damage) {
		defender.getHealth().proposeStatus(HealthStatus.VENOM, 6, Optional.of(attacker));
	}

	@Override
	public boolean isExecutable(Player operator) {
		return operator.getItems().isWearingItem(12926) && Misc.random(3) == 0;
	}

}
