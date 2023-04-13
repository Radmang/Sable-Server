package io.sable.content.minigames.tob.rooms;

import io.sable.content.instances.InstancedArea;
import io.sable.content.minigames.tob.TobConstants;
import io.sable.content.minigames.tob.TobRoom;
import io.sable.content.minigames.tob.bosses.Sotetseg;
import io.sable.model.collisionmap.WorldObject;
import io.sable.model.entity.player.Boundary;
import io.sable.model.entity.player.Player;
import io.sable.model.entity.player.Position;
import io.sable.model.world.objects.GlobalObject;

public class RoomFourSotetseg extends TobRoom {

    @Override
    public Sotetseg spawn(InstancedArea instancedArea) {
        return new Sotetseg(instancedArea);
    }

    @Override
    public Position getPlayerSpawnPosition() {
        return new Position(3280, 4293);
    }

    @Override
    public boolean handleClickObject(Player player, WorldObject worldObject, int option) {
        return false;
    }

    @Override
    public void handleClickBossGate(Player player, WorldObject worldObject) {
        if (player.getY() >= 4308) {
            player.getPA().movePlayer(player.getX(), 4306, player.getHeight());
        } else {
            player.getPA().movePlayer(player.getX(), 4308, player.getHeight());
        }
    }

    @Override
    public boolean isRoomComplete(InstancedArea instancedArea) {
        return instancedArea.getNpcs().isEmpty();
    }

    @Override
    public Boundary getBoundary() {
        return TobConstants.SOTETSEG_BOSS_ROOM_BOUNDARY;
    }

    @Override
    public Position getDeathPosition() {
        return new Position(3279, 4306, 0);
    }

    @Override
    public Position getFightStartPosition() {
        return new Position(3279, 4308, 0);
    }

    @Override
    public GlobalObject getFoodChestPosition() {
        return getFoodChest(new Position(3278, 4293, 0), 0);
    }
}
