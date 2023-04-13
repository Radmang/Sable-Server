package io.sable.content.commands.all;

import java.util.Optional;

import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;

/**
 * Opens the help interface.
 * 
 * @author Emiel
 */
public class Help extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		c.getPA().closeAllWindows();
		c.getPA().showInterface(59525);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Creates a help ticket");
	}

}
