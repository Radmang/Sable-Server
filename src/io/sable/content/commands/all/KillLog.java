package io.sable.content.commands.all;

import java.util.Optional;

import io.sable.content.combat.stats.MonsterKillLog;
import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;

public class KillLog extends Command {
    @Override
    public void execute(Player player, String commandName, String input) {
        MonsterKillLog.openInterface(player);
    }

    @Override
    public Optional<String> getDescription() {
        return Optional.of("Opens the kill log.");
    }

}
