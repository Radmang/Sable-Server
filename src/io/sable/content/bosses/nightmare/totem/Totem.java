package io.sable.content.bosses.nightmare.totem;

import io.sable.content.instances.InstancedArea;
import io.sable.model.CombatType;
import io.sable.model.definitions.NpcStats;
import io.sable.model.entity.Entity;
import io.sable.model.entity.npc.NPC;
import io.sable.model.entity.player.Player;

public class Totem extends NPC {

    private final TotemSpawn totemSpawn;

    public Totem(TotemSpawn totemSpawn, int npcId, InstancedArea instancedArea) {
        super(npcId, instancedArea.resolve(totemSpawn.getPosition()));
        this.totemSpawn = totemSpawn;
        getHealth().setMaximumHealth(0);
        getHealth().setCurrentHealth(0);
        instancedArea.add(this);
        getBehaviour().setWalkHome(false);
        getBehaviour().setRespawn(false);
        setDefaultNpcStats(NpcStats.builder().setRangeDef(300).setCrush(300).setSlash(300).setSlashDef(300).createNpcStats());
    }

    @Override
    public boolean canBeAttacked(Entity entity) {
        return getNpcId() == totemSpawn.getAttackableNpcId();
    }

    @Override
    public void setDead(boolean dead) {
        // ignore
    }

    @Override
    public void process() {
        if (getNpcId() == totemSpawn.getAttackableNpcId() && getHealth().getCurrentHealth() == 0) {
            requestTransform(totemSpawn.getChargedNpcId());
        }
    }

    @Override
    public int getAttackDistanceModifier(Player player, CombatType combatType) {
        return combatType == CombatType.MAGE ? 6 : 0;
    }

    public TotemSpawn getTotemSpawn() {
        return totemSpawn;
    }
}
