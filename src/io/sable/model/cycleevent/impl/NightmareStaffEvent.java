package io.sable.model.cycleevent.impl;

import io.sable.model.cycleevent.Event;
import io.sable.model.entity.player.Player;

public class NightmareStaffEvent extends Event<Player> {

    public NightmareStaffEvent(Player attachment) {
        super("volatile_nightmare_staff", attachment, 1);
    }

    @Override
    public void execute() {
        if (attachment == null || attachment.isDisconnected()) {
            super.stop();
            return;
        }
        if (attachment.playerEquipment[Player.playerWeapon] != 24424) {
            super.stop();
            return;
        }
        if (super.getElapsedTicks() > 100) {
            super.stop();
            return;
        }
    }
}