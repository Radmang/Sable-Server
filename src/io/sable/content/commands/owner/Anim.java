package io.sable.content.commands.owner;

import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;

public class Anim extends Command {

	@Override
	public void execute(Player player, String commandName, String input) {
		int id = Integer.parseInt(input);
		player.startAnimation(id);
		player.sendMessage("Playing animation: " + id);
	}

}
