package io.sable.content.commands.admin;

import io.sable.content.commands.Command;
import io.sable.content.worldevent.WorldEventContainer;
import io.sable.content.worldevent.impl.HesporiWorldEvent;
import io.sable.model.entity.player.Player;

public class Starthespori extends Command {
    @Override
    public void execute(Player player, String commandName, String input) {
        WorldEventContainer.getInstance().startEvent(new HesporiWorldEvent());
        player.sendMessage("Hespori will start soon.");
    }
}
