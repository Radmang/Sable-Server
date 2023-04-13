package io.sable.content.commands.all;

import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;

import java.util.Optional;

/**
 * Opens the game rule page in the default web browser.
 * 
 * @author Emiel
 */
public class Grules extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		c.getPA().sendFrame126("https://www.xeros.io/index.php?/topic/403-rules-guide-for-gambling-on-xeros/", 12000);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Opens a web page with in-game rules");
	}

}
