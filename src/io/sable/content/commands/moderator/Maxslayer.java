package io.sable.content.commands.moderator;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;
import io.sable.model.entity.player.PlayerHandler;

public class Maxslayer extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		Optional<Player> op = PlayerHandler.nonNullStream().filter(Objects::nonNull).max(Comparator.comparing(client -> client.getSlayer().getPoints()));
		op.ifPresent(player -> c.sendMessage("Highest slayer points: "+ player.getDisplayName() +" - "+player.getSlayer().getPoints()));
	}

}
