package io.sable.content.dialogue.impl;

import io.sable.content.FireOfDestruction;
import io.sable.content.dialogue.DialogueBuilder;
import io.sable.content.dialogue.DialogueOption;
import io.sable.model.entity.player.Player;
import io.sable.model.items.ItemAssistant;

public class FireOfDestructionDialogue extends DialogueBuilder {


    public FireOfDestructionDialogue(Player player, int itemId) {
        super(player);
        setNpcId(8208);
        if (itemId == -1) {
            npc("This fire has a 1/5 chance to upgrade"," your pet to its dark version.")
                    .npc("@red@A unsuccessful sacrifice will result in the", "@red@loss of your pet and no upgrade.")
                    .npc("Use your pet on the fire when you are ready.");
        } else if (FireOfDestruction.canBurn(itemId)) {
            npc("This fire has a 1/5 chance to upgrade", " your pet to its dark version.")
                    .npc("@red@A unsuccessful sacrifice will result in the", "@red@loss of your pet and no upgrade.")
                    .option(new DialogueOption("Burn my @red@" + ItemAssistant.getItemName(itemId) + " @bla@, I understand that I could get nothing.", p -> FireOfDestruction.burn(player, itemId)),
                            new DialogueOption("Can you explain again?", p -> player.start(new FireOfDestructionDialogue(player, itemId))
                            ));
        } else {
            player.getPA().closeAllWindows();
            npc("You cannot burn this item here.");
        }
    }
}
