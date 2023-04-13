package io.sable.content.commands.moderator;

import io.sable.content.commands.Command;
import io.sable.content.commands.punishment.PunishmentCommand;
import io.sable.model.entity.player.Player;

import java.util.Optional;

public class NetBan extends Command {
    @Override
    public void execute(Player player, String commandName, String input) {
        new PunishmentCommand(commandName, input).parse(player);
    }

    public Optional<String> getDescription() {
        return Optional.of("Ban all known addresses of an online player.");
    }

    @Override
    public String getFormat() {
        return PunishmentCommand.getFormat(getCommand());
    }
}
