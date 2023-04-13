package io.sable.content.commands.test;

import java.io.IOException;
import java.util.Optional;

import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;

public class EquipmentSetup extends Command {
    @Override
    public void execute(Player player, String commandName, String input) {
        try {
            io.sable.model.EquipmentSetup.equip(player, input);
        } catch (IOException e) {
            e.printStackTrace();
            player.sendMessage("Could not equip equipment setup.");
        }
    }

    public Optional<String> getDescription() {
        return Optional.of("Spawn a specific equipment setup.");
    }
}
