package io.sable.content.commands.owner;

import io.sable.content.commands.Command;
import io.sable.content.tradingpost.Listing;
import io.sable.model.entity.player.Player;

public class Post extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		Listing.openPost(c, false);
	}
}
