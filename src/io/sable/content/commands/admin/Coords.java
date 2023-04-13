package io.sable.content.commands.admin;

import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;

public class Coords extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		c.sendMessage("<col=ff0000>Coords: X="+c.absX+" Y="+c.absY+" Z="+c.heightLevel);
	}

}
