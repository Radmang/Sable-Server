package io.sable.content.commands.helper;

import io.sable.content.commands.Command;
import io.sable.content.help.HelpDatabase;
import io.sable.model.entity.player.Player;

/**
 * Opens an interface containing all help tickets.
 * 
 * @author Emiel
 */
public class Helpdb extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		HelpDatabase.getDatabase().openDatabase(c);
	}
}
