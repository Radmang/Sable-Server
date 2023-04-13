package io.sable.content.item.lootable;

import java.util.List;
import java.util.Map;

import io.sable.model.entity.player.Player;
import io.sable.model.items.GameItem;

public interface Lootable {

    Map<LootRarity, List<GameItem>> getLoot();

    void roll(Player player);

}
