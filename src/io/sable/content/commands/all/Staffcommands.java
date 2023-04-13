package io.sable.content.commands.all;

import java.util.Optional;

import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;

/**
 * Shows a list of commands.
 * 
 * @author Emiel
 *
 */
public class Staffcommands extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		if (!c.getRights().hasStaffPosition()) {
			c.sendMessage("You aren't a staff member!");
		} else {
			Commands.displayCommandsInterface(c, "all", "donator");
		}
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Shows a list of all commands");
	}

}
