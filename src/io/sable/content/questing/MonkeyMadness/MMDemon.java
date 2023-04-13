package io.sable.content.questing.MonkeyMadness;

import io.sable.model.entity.npc.NPC;
import io.sable.model.entity.player.Position;

public class MMDemon extends NPC {

    public MMDemon(Position position) {
        super(1443, position);
        getBehaviour().setAggressive(true);
    }
}
