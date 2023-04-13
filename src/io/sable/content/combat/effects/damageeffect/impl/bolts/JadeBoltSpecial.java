package io.sable.content.combat.effects.damageeffect.impl.bolts;

import io.sable.content.combat.Damage;
import io.sable.content.combat.effects.damageeffect.DamageBoostingEffect;
import io.sable.content.combat.range.RangeData;
import io.sable.model.Items;
import io.sable.model.entity.Entity;
import io.sable.model.entity.npc.NPC;
import io.sable.model.entity.player.Player;
import io.sable.util.Misc;

public class JadeBoltSpecial implements DamageBoostingEffect {

	@Override
	public void execute(Player attacker, Player defender, Damage damage) {
		int change = Misc.random(damage.getAmount());
		damage.setAmount(change);
		RangeData.createCombatGraphic(defender, 755, false);
		defender.freezeTimer = 33;
		// Find knocking down emote
	}

	@Override
	public void execute(Player attacker, NPC defender, Damage damage) {
		if (defender.getDefinition().getName() == null) {
			return;
		}
		RangeData.createCombatGraphic(defender, 755, false);
		defender.freezeTimer = 33;
	}

	@Override
	public boolean isExecutable(Player operator) {
		return RangeData.boltSpecialAvailable(operator, Items.JADE_BOLTS_E, Items.JADE_DRAGON_BOLTS_E);
	}

	@Override
	public double getMaxHitBoost(Player attacker, Entity defender) {
		return 0;
	}

}
