package io.sable.content.bosses.mimic;

import io.sable.content.combat.npc.NPCAutoAttack;
import io.sable.content.combat.npc.NPCAutoAttackBuilder;
import io.sable.content.combat.npc.NPCCombatAttack;
import io.sable.model.Animation;
import io.sable.model.CombatType;
import io.sable.model.ProjectileBase;
import io.sable.model.ProjectileBaseBuilder;

import java.util.function.Function;

public class ThirdAgeMager implements Function<MimicNpc, NPCAutoAttack> {

    private static ProjectileBase projectile() {
        return new ProjectileBaseBuilder()
                .setSendDelay(2)
                .setSpeed(25)
                .setStartHeight(40)
                .setProjectileId(165)
                .createProjectileBase();
    }

    @Override
    public NPCAutoAttack apply(MimicNpc nightmare) {
        return new NPCAutoAttackBuilder()
                .setSelectPlayersForMultiAttack(NPCAutoAttack.getDefaultSelectPlayersForAttack())
                .setAnimation(new Animation(1167))
                .setCombatType(CombatType.MAGE)
                .setMaxHit(16)
                .setHitDelay(3)
                .setAttackDelay(4)
                .setDistanceRequiredForAttack(24)
                .setMultiAttack(false)
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
