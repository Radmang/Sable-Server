package io.sable.content.commands.owner;

import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;

/**
 * Show a red skull above the player's head.
 * 
 * @author Emiel
 *
 */
public class Red extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		c.headIconPk = (1);
		c.getPA().requestUpdates();
	}
}
