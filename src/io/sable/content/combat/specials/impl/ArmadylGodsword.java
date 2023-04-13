package io.sable.content.combat.specials.impl;

import io.sable.content.combat.Damage;
import io.sable.content.combat.specials.Special;
import io.sable.model.definitions.AnimationLength;
import io.sable.model.entity.Entity;
import io.sable.model.entity.player.Player;

public class ArmadylGodsword extends Special {

	public ArmadylGodsword() {
		super(5.0, 2.0, 1.375, new int[] { 11802, 20368 });
	}

	@Override
	public void activate(Player player, Entity target, Damage damage) {
		int animationDelay = AnimationLength.getFrameLength(7644) + 1;
		player.getAnimationTimer().setDuration(animationDelay);
		player.startAnimation(7644);
		player.gfx0(1211);
	}

	@Override
	public void hit(Player player, Entity target, Damage damage) {

	}

}
