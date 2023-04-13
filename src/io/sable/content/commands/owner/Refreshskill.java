package io.sable.content.commands.owner;

import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;

public class Refreshskill extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		c.getPA().refreshSkill(Integer.parseInt(input));
	}

}
