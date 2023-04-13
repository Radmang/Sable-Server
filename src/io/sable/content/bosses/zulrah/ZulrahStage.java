package io.sable.content.bosses.zulrah;

import io.sable.model.cycleevent.CycleEvent;
import io.sable.model.entity.player.Player;

public abstract class ZulrahStage extends CycleEvent {

	protected Zulrah zulrah;

	protected Player player;

	public ZulrahStage(Zulrah zulrah, Player player) {
		this.zulrah = zulrah;
		this.player = player;
	}

}
