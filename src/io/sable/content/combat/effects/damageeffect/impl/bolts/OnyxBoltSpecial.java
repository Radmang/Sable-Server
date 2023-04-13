package io.sable.content.combat.effects.damageeffect.impl.bolts;

import io.sable.Configuration;
import io.sable.content.combat.Damage;
import io.sable.content.combat.effects.damageeffect.DamageBoostingEffect;
import io.sable.content.combat.range.RangeData;
import io.sable.model.Items;
import io.sable.model.entity.Entity;
import io.sable.model.entity.npc.NPC;
import io.sable.model.entity.player.Player;
import io.sable.util.Misc;

public class OnyxBoltSpecial implements DamageBoostingEffect {

	@Override
	public void execute(Player attacker, Player defender, Damage damage) {
		int change = Misc.random((int) (damage.getAmount() * 1.25));
		damage.setAmount(change);
		RangeData.createCombatGraphic(defender, 753, false);
		attacker.getHealth().increase(change / 4);
		attacker.getPA().refreshSkill(3);
	}

	@Override
	public void execute(Player attacker, NPC defender, Damage damage) {
		if (defender.getDefinition().getName() == null) {
			return;
		}
		if (Misc.linearSearch(Configuration.UNDEAD_NPCS, defender.getNpcId()) != -1) {
			return;
		}
		RangeData.createCombatGraphic(defender, 753, false);
		attacker.getHealth().increase(damage.getAmount() / 4);
		attacker.getPA().refreshSkill(3);
	}

	@Override
	public boolean isExecutable(Player operator) {
		return RangeData.boltSpecialAvailable(operator, Items.ONYX_BOLTS_E, Items.ONYX_DRAGON_BOLTS_E);
	}

	@Override
	public double getMaxHitBoost(Player attacker, Entity defender) {
		return 0.25;
	}

}
