package io.sable.content.commands.owner;

import io.sable.content.bosses.Cerberus;
import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;

/**
 * Update the shops.
 * 
 * @author Emiel
 *
 */
public class Startcerberus extends Command {

	@Override
	public void execute(Player player, String commandName, String input) {
		Cerberus.init(player);
	}
}
