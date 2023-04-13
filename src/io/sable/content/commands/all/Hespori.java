package io.sable.content.commands.all;

import java.util.Optional;

import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;

/**
 * Teleport the player to the mage bank.
 * 
 * @author Emiel
 */
public class Hespori extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
    	c.getPA().sendFrame126("https://www.xeros.io/index.php?/topic/107-hespori-skilling-boss-guide/", 12000);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Opens up the Hespori boss guide.");
	}

}
