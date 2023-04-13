package io.sable.content.commands.owner;

import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;

public class Addxp extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		String[] args = input.split(" ");
		c.getPA().addSkillXP(Integer.parseInt(args[1]), Integer.parseInt(args[0]), true);
	}

}
