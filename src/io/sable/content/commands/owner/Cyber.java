package io.sable.content.commands.owner;

import io.sable.Configuration;
import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;

/**
 * Toggle the Cyber Monday sale on or off.
 * 
 * @author Emiel
 *
 */
public class Cyber extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		Configuration.CYBER_MONDAY = !Configuration.CYBER_MONDAY;
		String status = Configuration.CYBER_MONDAY ? "On" : "Off";
		c.sendMessage("Cyber monday: " + status);
	}
}
