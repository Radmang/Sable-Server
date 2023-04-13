package io.sable.content.commands.all;

import java.util.Optional;

import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;

/**
 * Toggles whether a warning will be shown when attempting to drop an item on the ground.
 *
 * @author Emiel
 *
 */
public class Togglealch extends Command {

    @Override
    public void execute(Player c, String commandName, String input) {
        c.setAlchWarning(!c.isAlchWarning());
        c.sendMessage("You will {} be warned when attempting to alch a valuable item.", c.isAlchWarning() ? "now" : "@red@not@bla@");
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.of("Toggles the item drop warning on or off");
    }

}
