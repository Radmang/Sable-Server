package io.sable.content.bosses.zulrah.impl;

import java.util.Arrays;

import io.sable.content.bosses.zulrah.DangerousEntity;
import io.sable.content.bosses.zulrah.DangerousLocation;
import io.sable.content.bosses.zulrah.SpawnDangerousEntity;
import io.sable.content.bosses.zulrah.Zulrah;
import io.sable.content.bosses.zulrah.ZulrahLocation;
import io.sable.content.bosses.zulrah.ZulrahStage;
import io.sable.model.CombatType;
import io.sable.model.cycleevent.CycleEventContainer;
import io.sable.model.cycleevent.CycleEventHandler;
import io.sable.model.entity.player.Player;

public class CreateToxicStageOne extends ZulrahStage {

	public CreateToxicStageOne(Zulrah zulrah, Player player) {
		super(zulrah, player);
	}

	@Override
	public void execute(CycleEventContainer container) {
		if (container.getOwner() == null || zulrah == null || zulrah.getNpc() == null || zulrah.getNpc().isDead() || player == null || player.isDead
				|| zulrah.getInstancedZulrah() == null) {
			container.stop();
			return;
		}
		if (container.getTotalTicks() == 1) {
			zulrah.getNpc().setFacePlayer(false);
			CycleEventHandler.getSingleton().addEvent(player, new SpawnDangerousEntity(zulrah, player, Arrays.asList(DangerousLocation.values()),
							DangerousEntity.TOXIC_SMOKE, 40),1);
		} else if (container.getTotalTicks() >= 19) {
			zulrah.getNpc().totalAttacks = 0;
			zulrah.changeStage(2, CombatType.MELEE, ZulrahLocation.NORTH);
			container.stop();
		}
	}

}
