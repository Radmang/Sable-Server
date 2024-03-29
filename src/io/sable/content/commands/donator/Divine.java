package io.sable.content.commands.donator;

import java.util.Optional;

import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;
import io.sable.model.entity.player.Right;

public class Divine extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		if (c.inTrade || c.inDuel || c.getPosition().inWild()) {
			return;
		}

		if (c.amDonated < 2500 && !c.getRights().isOrInherits(Right.ADMINISTRATOR)) {
			c.sendMessage("@red@You need to be a divine donator to do this command");
			return;
		}
		c.getPA().startTeleport(3263, 6089, 2, "modern", false);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Teleports you to divine zone.");
	}

}


