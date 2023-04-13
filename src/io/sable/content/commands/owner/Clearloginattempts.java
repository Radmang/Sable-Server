package io.sable.content.commands.owner;

import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;
import io.sable.net.login.LoginThrottler;

public class Clearloginattempts extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		LoginThrottler.clear();
		c.sendMessage("Cleared all login attempts.");
	}

}
