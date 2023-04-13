package io.sable.content.bosses.zulrah.impl;

import io.sable.content.bosses.zulrah.Zulrah;
import io.sable.content.bosses.zulrah.ZulrahLocation;
import io.sable.content.bosses.zulrah.ZulrahStage;
import io.sable.model.CombatType;
import io.sable.model.cycleevent.CycleEventContainer;
import io.sable.model.entity.player.Player;

public class RangeStageSeven extends ZulrahStage {

	public RangeStageSeven(Zulrah zulrah, Player player) {
		super(zulrah, player);
	}

	@Override
	public void execute(CycleEventContainer container) {
		if (container.getOwner() == null || zulrah == null || zulrah.getNpc() == null || zulrah.getNpc().isDead() || player == null || player.isDead
				|| zulrah.getInstancedZulrah() == null) {
			container.stop();
			return;
		}
		if (zulrah.getNpc().totalAttacks > 5) {
			player.getZulrahEvent().changeStage(8, CombatType.MAGE, ZulrahLocation.SOUTH);
			zulrah.getNpc().totalAttacks = 0;
			container.stop();
			return;
		}
	}

}
