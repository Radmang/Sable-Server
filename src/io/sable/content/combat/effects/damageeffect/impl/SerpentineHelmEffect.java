package io.sable.content.combat.effects.damageeffect.impl;

import java.util.Optional;

import io.sable.content.combat.Damage;
import io.sable.content.combat.effects.damageeffect.DamageEffect;
import io.sable.model.entity.HealthStatus;
import io.sable.model.entity.npc.NPC;
import io.sable.model.entity.player.Player;
import io.sable.util.Misc;

public class SerpentineHelmEffect implements DamageEffect {

	@Override
	public void execute(Player attacker, Player defender, Damage damage) {
		attacker.getHealth().proposeStatus(HealthStatus.VENOM, 6, Optional.of(defender));
	}

	@Override
	public void execute(Player attacker, NPC defender, Damage damage) {
		defender.getHealth().proposeStatus(HealthStatus.VENOM, 6, Optional.of(attacker));
	}

	@Override
	public boolean isExecutable(Player operator) {
		return (operator.getItems().isWearingItem(12931) || operator.getItems().isWearingItem(13199) || operator.getItems().isWearingItem(13197)) && Misc.random(5) == 1;
	}

}
