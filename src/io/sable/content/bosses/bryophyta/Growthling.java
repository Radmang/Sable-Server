package io.sable.content.bosses.bryophyta;

import io.sable.model.entity.Entity;
import io.sable.model.entity.npc.NPC;
import io.sable.model.entity.player.Player;
import io.sable.model.entity.player.Position;

public class Growthling extends NPC {

    public Growthling(int npcId, Position position, Player spawnedBy) {
        super(npcId, position);
        this.spawnedBy = spawnedBy.getIndex();
    }

    @Override
    public boolean canBeAttacked(Entity entity) {
        if (this.spawnedBy != entity.getIndex()) {
            if (entity instanceof Player) {
                Player p = (Player) entity;
                if (p != null)
                    p.sendMessage(this.getName()+" isn't after you.");
            }
            return false;
        }
        return true;
    }
}
