package io.sable.content.commands.all;

import java.util.Optional;

import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;

/**
 * Opens the store page in the default web browser.
 * 
 * @author Emiel
 */
public class Donate extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {

	}
	@Override
	public Optional<String> getDescription() {
		return Optional.of("Opens store");
	}

}
