package io.sable.content.bosses.nightmare.attack;

import java.util.function.Function;

import io.sable.content.bosses.nightmare.Nightmare;
import io.sable.content.combat.npc.NPCAutoAttack;
import io.sable.content.combat.npc.NPCAutoAttackBuilder;
import io.sable.content.combat.npc.NPCCombatAttack;
import io.sable.model.Animation;
import io.sable.model.CombatType;
import io.sable.model.ProjectileBase;
import io.sable.model.ProjectileBaseBuilder;

public class StandardRanged implements Function<Nightmare, NPCAutoAttack> {

    private static ProjectileBase projectile() {
        return new ProjectileBaseBuilder()
                .setSendDelay(4)
                .setSpeed(15)
                .setStartHeight(90)
                .setProjectileId(1766)
                .createProjectileBase();
    }

    @Override
    public NPCAutoAttack apply(Nightmare nightmare) {
        return new NPCAutoAttackBuilder()
                .setSelectPlayersForMultiAttack(NPCAutoAttack.getDefaultSelectPlayersForAttack())
                .setAnimation(new Animation(8596))
                .setCombatType(CombatType.RANGE)
                .setMaxHit(30)
                .setHitDelay(4)
                .setAttackDelay(6)
                .setDistanceRequiredForAttack(16)
                .setMultiAttack(true)
                //.setOnAttack(StandardMage.getProjectileThrow(projectile()))
                .setPrayerProtectionPercentage(new Function<NPCCombatAttack, Double>() {
                    @Override
                    public Double apply(NPCCombatAttack npcCombatAttack) {
                        return 0.3;
                    }
                })
                .setProjectile(projectile())
                .createNPCAutoAttack();
    }
}
