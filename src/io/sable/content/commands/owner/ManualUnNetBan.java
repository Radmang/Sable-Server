package io.sable.content.commands.owner;

import io.sable.Server;
import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;
import io.sable.punishments.PunishmentType;

public class ManualUnNetBan extends Command {
    @Override
    public void execute(Player player, String commandName, String input) {
        Server.getPunishments().removeWithMessage(player, PunishmentType.NET_BAN, input);
        Server.getPunishments().removeWithMessage(player, PunishmentType.MAC_BAN, input);
    }
}
