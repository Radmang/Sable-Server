package io.sable.content.commands.punishment.impl;

import io.sable.content.commands.punishment.OnlinePlayerPunishmentPCP;
import io.sable.model.entity.player.Player;
import io.sable.punishments.PunishmentType;
import io.sable.util.dateandtime.TimeSpan;

public class Mute extends OnlinePlayerPunishmentPCP {

    @Override
    public String name() {
        return "mute";
    }

    @Override
    public PunishmentType getPunishmentType() {
        return PunishmentType.MUTE;
    }

    @Override
    public void onPunishment(Player staff, Player player, TimeSpan duration) {
        player.muteEnd = System.currentTimeMillis() + duration.toMillis();
        player.sendMessage("@red@You have been muted by {} for {}.", staff.getDisplayNameFormatted(), duration.toString());
    }

    @Override
    public void onRemovePunishment(Player staff, Player player) {
        player.muteEnd = 0;
    }

    @Override
    public String extract(Player player) {
        return player.getDisplayNameLower();
    }
}
