package io.sable.model.cycleevent.impl;

import io.sable.model.cycleevent.Event;
import io.sable.model.entity.Entity;
import io.sable.model.entity.Health;
import io.sable.model.entity.HealthStatus;

public class VenomResistanceEvent extends Event<Entity> {

	public VenomResistanceEvent(Entity attachment, int ticks) {
		super("venom_resistance_event", attachment, ticks);
	}

	@Override
	public void execute() {
		super.stop();
		if (attachment == null) {
			return;
		}
		Health health = attachment.getHealth();
		health.removeNonsusceptible(HealthStatus.VENOM);
	}

}
