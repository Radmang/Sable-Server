package io.sable.content.commands.test;

import java.util.Optional;

import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;

public class PrintMillis extends Command {
    @Override
    public void execute(Player player, String commandName, String input) {
        player.sendMessage("" + System.currentTimeMillis());
        System.out.println(System.currentTimeMillis());
    }

    public Optional<String> getDescription() {
        return Optional.of("Prints milliseconds to chat and console.");
    }
}
