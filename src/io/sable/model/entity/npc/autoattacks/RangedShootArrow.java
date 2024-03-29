package io.sable.model.entity.npc.autoattacks;

import io.sable.content.combat.npc.NPCAutoAttackBuilder;
import io.sable.model.Animation;
import io.sable.model.CombatType;
import io.sable.model.Graphic;
import io.sable.model.ProjectileBaseBuilder;

public class RangedShootArrow extends NPCAutoAttackBuilder {

    public RangedShootArrow(int maxHit) {
        setAttackDelay(3);
        setMaxHit(maxHit);
        setAnimation(new Animation(426));
        setCombatType(CombatType.RANGE);
        setDistanceRequiredForAttack(10);
        setHitDelay(3);
        setStartGraphic(new Graphic(19, Graphic.GraphicHeight.MIDDLE));
        setProjectile(new ProjectileBaseBuilder().setSendDelay(2).setProjectileId(11).createProjectileBase());
    }
}
