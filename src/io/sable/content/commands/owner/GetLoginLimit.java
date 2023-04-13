package io.sable.content.commands.owner;

import io.sable.content.commands.Command;
import io.sable.model.entity.player.Player;
import io.sable.net.login.LoginRequestLimit;

public class GetLoginLimit extends Command {
    @Override
    public void execute(Player player, String commandName, String input) {
       player.sendMessage("Login rate limit is set to {}", "" + LoginRequestLimit.MAX_LOGINS_PER_TICK);
    }
}
