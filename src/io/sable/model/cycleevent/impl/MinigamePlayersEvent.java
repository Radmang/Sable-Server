package io.sable.model.cycleevent.impl;

import java.util.concurrent.TimeUnit;

import io.sable.model.cycleevent.Event;
import io.sable.model.entity.player.Player;
import io.sable.util.Misc;

public class MinigamePlayersEvent extends Event<Object> {
	
	/**
	 * The amount of time in game cycles (600ms) that the event pulses at
	 */
	private static final int INTERVAL = Misc.toCycles(5, TimeUnit.SECONDS);

	/**
	 * Creates a new event to cycle through messages for the entirety of the runtime
	 */
	public MinigamePlayersEvent(Player attachment) {
		super(attachment, INTERVAL);
	}

	@Override
	public void execute() {
		//((Player) attachment).getPA().sendFrame126("@or1@- PK District: @whi@" + (Boundary.entitiesInArea(Boundary.CLAN_WARS) + Boundary.entitiesInArea(Boundary.CLAN_WARS_SAFE)), 29178);
		//((Player) attachment).getPA().sendFrame126("@or1@- Wilderness: @whi@" + (Boundary.entitiesInArea(Boundary.WILDERNESS) + Boundary.entitiesInArea(Boundary.WILDERNESS_UNDERGROUND) + Boundary.entitiesInArea(Boundary.WILDERNESS_GOD_WARS_BOUNDARY)), 29179);
		//((Player) attachment).getPA().sendFrame126("@or1@- Pest Control: @whi@" + Boundary.entitiesInArea(Boundary.PEST_CONTROL_AREA), 29180);
	}
}
