package io.sable.content.minigames.tob;

import io.sable.content.instances.InstancedArea;
import io.sable.model.collisionmap.WorldObject;
import io.sable.model.entity.player.Boundary;
import io.sable.model.entity.player.Player;
import io.sable.model.entity.player.Position;
import io.sable.model.world.objects.GlobalObject;

public abstract class TobRoom {

    public abstract TobBoss spawn(InstancedArea instancedArea);

    public abstract Position getPlayerSpawnPosition();

    public abstract boolean handleClickObject(Player player, WorldObject worldObject, int option);

    public abstract void handleClickBossGate(Player player, WorldObject worldObject);

    public abstract boolean isRoomComplete(InstancedArea instancedArea);

    public abstract Boundary getBoundary();

    public abstract Position getDeathPosition();

    public abstract Position getFightStartPosition();

    public abstract GlobalObject getFoodChestPosition();

    public GlobalObject getFoodChest(Position position, int face) {
        return new GlobalObject(TobConstants.FOOD_CHEST_OBJECT_ID, position, face, 10);
    }
}
