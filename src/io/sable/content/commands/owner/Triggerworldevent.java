package io.sable.content.commands.owner;

import io.sable.content.commands.Command;
import io.sable.content.worldevent.WorldEventContainer;
import io.sable.model.entity.player.Player;

public class Triggerworldevent extends Command {
    @Override
    public void execute(Player player, String commandName, String input) {
        WorldEventContainer.getInstance().setTriggerImmediateEvent(true);
        player.sendMessage("Triggering next world event, please allow up to 30 seconds.");
    }
}
