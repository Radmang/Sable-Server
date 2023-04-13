package io.sable.model.entity.npc.autoattacks;

import io.sable.content.combat.npc.NPCAutoAttackBuilder;
import io.sable.model.Animation;
import io.sable.model.CombatType;

public class MeleeSwordSwing extends NPCAutoAttackBuilder {

    public MeleeSwordSwing(int maxHit) {
        setAttackDelay(4);
        setMaxHit(maxHit);
        setAnimation(new Animation(451));
        setCombatType(CombatType.MELEE);
    }
}
