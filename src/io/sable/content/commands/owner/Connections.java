package io.sable.content.commands.owner;

import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;
import io.sable.net.ChannelHandler;

public class Connections extends Command {
    @Override
    public void execute(Player player, String commandName, String input) {
       player.sendMessage("There are currently {} active connections.", "" + ChannelHandler.getActiveConnections());
    }
}
