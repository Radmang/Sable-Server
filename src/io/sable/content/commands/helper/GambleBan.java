package io.sable.content.commands.helper;

import io.sable.content.commands.Command;
import io.sable.content.commands.punishment.PunishmentCommand;
import io.sable.model.entity.player.Player;

import java.util.Optional;

/**
 * Forces a given player to log out.
 * 
 * @author Emiel
 */
public class GambleBan extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		new PunishmentCommand(commandName, input).parse(c);
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Ban a player from gambling.");
	}

	@Override
	public String getFormat() {
		return PunishmentCommand.getFormat(getCommand());
	}

}
