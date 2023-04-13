package io.sable.model.entity.npc.autoattacks;

import io.sable.content.combat.npc.NPCAutoAttackBuilder;
import io.sable.model.Animation;
import io.sable.model.CombatType;
import io.sable.model.Graphic;
import io.sable.model.ProjectileBaseBuilder;

public class MagicAirStrike extends NPCAutoAttackBuilder {

    public MagicAirStrike(int maxHit) {
        setAttackDelay(5);
        setMaxHit(maxHit);
        setAnimation(new Animation(711));
        setCombatType(CombatType.RANGE);
        setDistanceRequiredForAttack(10);
        setHitDelay(3);
        setStartGraphic(new Graphic(90, Graphic.GraphicHeight.MIDDLE));
        setEndGraphic(new Graphic(92, Graphic.GraphicHeight.MIDDLE));
        setProjectile(new ProjectileBaseBuilder().setSendDelay(3).setProjectileId(91).createProjectileBase());
    }
}
