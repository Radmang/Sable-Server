package io.sable.content.commands.all;

import java.util.Optional;

import io.sable.Configuration;
import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;

/**
 * Opens the game rule page in the default web browser.
 * 
 * @author Emiel
 */
public class Rules extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		c.getPA().sendFrame126(Configuration.RULES_LINK, 12000);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Opens a web page with in-game rules");
	}

}
