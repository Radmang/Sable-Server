package io.sable.content.commands.helper;

import io.sable.content.commands.Command;
import io.sable.content.commands.punishment.PunishmentCommand;
import io.sable.model.entity.player.Player;

public class Unmute extends Command {
	@Override
	public void execute(Player c, String commandName, String input) {
		new PunishmentCommand(commandName, input).parse(c);
	}

	@Override
	public String getFormat() {
		return PunishmentCommand.getFormat(getCommand());
	}
}
