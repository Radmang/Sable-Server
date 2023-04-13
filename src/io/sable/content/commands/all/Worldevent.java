package io.sable.content.commands.all;

import java.util.Optional;

import io.sable.content.bosses.hespori.HesporiSpawner;
import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;

public class Worldevent extends Command {

	@Override
	public void execute(Player player, String commandName, String input) {
		if (HesporiSpawner.isSpawned()) {
			player.getPA().spellTeleport(3072, 3499, 0, false);
			player.setHesporiDamageCounter(0);
		} else {
			player.sendMessage("@red@[World Event] @bla@There is currently no world event going on.");
		}
	}

	@Override
	public Optional<String> getDescription() {
		return Optional.of("Teles you to world event.");
	}
}
