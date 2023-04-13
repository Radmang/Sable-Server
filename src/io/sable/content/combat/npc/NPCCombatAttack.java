package io.sable.content.combat.npc;

import io.sable.model.entity.Entity;
import io.sable.model.entity.npc.NPC;

public class NPCCombatAttack {

    private final NPC npc;
    private final Entity victim;

    public NPCCombatAttack(NPC npc, Entity victim) {
        this.npc = npc;
        this.victim = victim;
    }

    public NPC getNpc() {
        return npc;
    }

    public Entity getVictim() {
        return victim;
    }

}
