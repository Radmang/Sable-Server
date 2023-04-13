package io.sable.content.commands.all;

import java.util.Optional;

import io.sable.content.commands.Command;
import io.sable.model.entity.npc.drops.DropManager;
import io.sable.model.entity.player.Player;

public class Droprate extends Command {

    @Override
    public void execute(Player player, String commandName, String input) {
        player.forcedChat("My drop rate bonus is : " + DropManager.getModifier1(player) + "%.");
    }
	@Override
	public Optional<String> getDescription() {
		return Optional.of("Shows drop rate bonus");
	}
}


