package io.sable.content.commands.owner;

import io.sable.content.commands.Command;
import io.sable.model.entity.npc.NPCSpawning;
import io.sable.model.entity.player.Player;

/**
 * Spawn a specific Npc.
 * 
 * @author Emiel
 *
 */
public class Npc extends Command {

	@Override
	public void execute(Player c, String commandName, String input) {
		int newNPC = Integer.parseInt(input);
		if (newNPC > 0) {
			NPCSpawning.spawnNpc(c, newNPC, c.absX, c.absY, c.heightLevel, 0, 7, false, false);
			c.sendMessage("You spawn a Npc.");
		} else {
			c.sendMessage("No such NPC.");
		}
	}
}
