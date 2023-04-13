package io.sable.content.commands.donator;

import java.util.Optional;

import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;

/**
 * Changes the title of the player to their default donator title.
 * 
 * @author Emiel
 */
public class Donatortitle extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		c.sendMessage("You will now get your donator title instead. Relog for changes to take effect.");
		c.keepTitle = false;
		c.killTitle = false;
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Changes your player title");
	}

	@Override
	public Optional<String> getParameter() {
		return Optional.of("title");
	}

}
