package io.sable.content.commands.all;

import java.util.Optional;

import io.sable.content.commands.Command;
import io.sable.content.events.monsterhunt.MonsterHunt;
import io.sable.model.entity.player.Player;

public class Event extends Command {

	@Override
	public void execute(Player player, String commandName, String input) {
		if(MonsterHunt.getCurrentLocation() != null) {
			player.sendMessage("@red@[Wildy Pursuit] @bla@Current Location: " + MonsterHunt.getCurrentLocation().getLocationName());
			player.sendMessage("@red@[Wildy Pursuit] @bla@Current Monster: " + MonsterHunt.getName());
			player.sendMessage("@red@[Wildy Pursuit] @bla@ Type ::telepursuit to get to the monsters location. @red@Wilderness!!");
		} else {
			player.sendMessage("@red@[Wildy Pursuit] @bla@No monster is currently in pursuit.");
		}
		
	}
	@Override
	public Optional<String> getDescription() {
		return Optional.of("Teles you to wildy event");
	}
}
