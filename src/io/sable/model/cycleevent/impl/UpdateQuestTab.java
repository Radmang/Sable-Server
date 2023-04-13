package io.sable.model.cycleevent.impl;

import java.util.concurrent.TimeUnit;

import io.sable.model.cycleevent.Event;
import io.sable.model.entity.player.PlayerHandler;
import io.sable.util.Misc;

public class UpdateQuestTab extends Event<Object> {


	private static final int INTERVAL = Misc.toCycles(5, TimeUnit.SECONDS);

	
	public UpdateQuestTab() {
		super("", new Object(), INTERVAL);
	}	

	@Override
	public void execute() {
		PlayerHandler.nonNullStream().forEach(player -> { 
			player.getQuestTab().updateInformationTab();
		});
	}
} 