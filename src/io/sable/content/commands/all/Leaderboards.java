package io.sable.content.commands.all;

import io.sable.content.commands.Command;
import io.sable.content.leaderboards.LeaderboardInterface;
import io.sable.model.entity.player.Player;

import java.util.Optional;

public class Leaderboards extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		LeaderboardInterface.openInterface(c);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Opens the leaderboards interface.");
	}
}
