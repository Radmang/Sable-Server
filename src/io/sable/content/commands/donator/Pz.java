package io.sable.content.commands.donator;

import java.util.Optional;

import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;
import io.sable.model.entity.player.Right;

public class Pz extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		if (c.inTrade || c.inDuel || c.getPosition().inWild())  {
			return;
		}

		if (c.amDonated < 2500 && !c.getRights().isOrInherits(Right.ADMINISTRATOR)) {
			c.sendMessage("@red@You need to be a platinum donator to do this command");
			return;
		}
		c.getPA().startTeleport(2457, 2841, 2, "modern", false);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Teleports you to platinum zone.");
	}

}


