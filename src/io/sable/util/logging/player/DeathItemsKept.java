package io.sable.util.logging.player;

import io.sable.model.entity.player.Player;
import io.sable.model.entity.player.Position;
import io.sable.model.items.GameItem;
import io.sable.util.Misc;
import io.sable.util.logging.PlayerLog;

import java.util.List;
import java.util.Set;

public class DeathItemsKept extends PlayerLog {

    private final List<GameItem> kept;
    private final Position position;


    public DeathItemsKept(Player player, List<GameItem> kept) {
        super(player);
        this.kept = kept;
        this.position = player.getPosition();
    }

    @Override
    public Set<String> getLogFileNames() {
        return Set.of("unsafe_death");
    }

    @Override
    public String getLoggedMessage() {
        return Misc.replaceBracketsWithArguments("{} kept {}", position, kept);
    }
}
