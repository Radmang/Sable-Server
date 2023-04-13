package io.sable.content.commands.owner;

import io.sable.content.commands.Command;
import io.sable.model.Items;
import io.sable.model.entity.player.Player;
import io.sable.model.items.ImmutableItem;

/**
 * @author Arthur Behesnilian 1:26 PM
 */
public class Cash extends Command {

    @Override
    public void execute(Player player, String commandName, String input) {
        player.getInventory().addToInventory(new ImmutableItem(Items.COINS, Integer.MAX_VALUE));
        player.sendMessage("You spawn a stack of cash.");
    }

}
