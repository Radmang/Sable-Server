package io.sable.content.commands.donator;

import java.util.Optional;

import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;

/**
 * Teleports the player to the donator zone.
 * 
 * @author Emiel
 */
public class Donatorzone extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		if (c.inTrade || c.inDuel || c.getPosition().inWild()) {
			return;
		}
		c.getPA().startTeleport(3809, 2844, 0, "modern", false);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Teleports you to the donator zone");
	}

}
