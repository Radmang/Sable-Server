package io.sable.content.minigames.tob.rooms;

import io.sable.content.instances.InstancedArea;
import io.sable.content.minigames.tob.TobConstants;
import io.sable.content.minigames.tob.TobRoom;
import io.sable.content.minigames.tob.bosses.Verzik;
import io.sable.model.collisionmap.WorldObject;
import io.sable.model.entity.player.Boundary;
import io.sable.model.entity.player.Player;
import io.sable.model.entity.player.Position;
import io.sable.model.world.objects.GlobalObject;
import io.sable.util.Misc;

public class RoomSixVerzik extends TobRoom {

    private static final Position[] CAGES = {
            new Position(3157, 4325, 0),
            new Position(3159, 4325, 0),
            new Position(3161, 4325, 0),
            new Position(3175, 4325, 0),
            new Position(3177, 4325, 0),
            new Position(3179, 4325, 0),
    };

    @Override
    public Verzik spawn(InstancedArea instancedArea) {
        return new Verzik(instancedArea);
    }

    @Override
    public Position getPlayerSpawnPosition() {
        return new Position(3168, 4303, 0);
    }

    @Override
    public boolean handleClickObject(Player player, WorldObject worldObject, int option) {
        return false;
    }

    @Override
    public void handleClickBossGate(Player player, WorldObject worldObject) {

    }

    @Override
    public boolean isRoomComplete(InstancedArea instancedArea) {
        return instancedArea.getNpcs().isEmpty();
    }

    @Override
    public Boundary getBoundary() {
        return TobConstants.VERZIK_BOSS_ROOM_BOUNDARY;
    }

    @Override
    public Position getDeathPosition() {
        return CAGES[Misc.trueRand(CAGES.length)];
    }

    @Override
    public Position getFightStartPosition() {
        return null;
    }

    @Override
    public GlobalObject getFoodChestPosition() {
        return null;
    }
}
