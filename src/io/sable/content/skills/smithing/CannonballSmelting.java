package io.sable.content.skills.smithing;

import io.sable.content.dialogue.DialogueBuilder;
import io.sable.content.dialogue.types.MakeItemDialogue;
import io.sable.content.skills.Skill;
import io.sable.model.Items;
import io.sable.model.SkillLevel;
import io.sable.model.entity.player.Player;
import io.sable.model.items.ImmutableItem;
import io.sable.model.tickable.impl.ItemProductionTickableBuilder;

public class CannonballSmelting {

    public static boolean isSmeltingCannonballs(Player player) {
        return player.getItems().playerHasItem(Items.AMMO_MOULD);
    }

    public static void smelt(Player player) {
        player.start(new DialogueBuilder(player).makeItems(
                100, CannonballSmelting::make,
                new MakeItemDialogue.MakeItem(Items.CANNONBALL)
        ));
    }

    private static void make(MakeItemDialogue.PlayerMakeItem makeItem) {
        new ItemProductionTickableBuilder()
                .setPlayer(makeItem.getPlayer())
                .setProductionDelay(3)
                .setProductionAmount(makeItem.getAmount())
                .setExecutionConsumer(task -> task.getPlayer().startAnimation(899))
                .setItemsConsumed(new ImmutableItem(Items.STEEL_BAR))
                .setItemsProduced(new ImmutableItem(Items.CANNONBALL, 4))
                .setExperiencedGained(new SkillLevel(Skill.SMITHING, 25))
                .createItemProductionTask().begin();
    }

}
