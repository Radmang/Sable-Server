package io.sable.content.bosses;

import io.sable.model.entity.npc.NPC;
import io.sable.model.entity.npc.NPCSpawning;
import io.sable.model.entity.player.Boundary;
import io.sable.model.entity.player.Coordinate;
import io.sable.model.entity.player.Player;
import io.sable.model.entity.player.Right;

/**
 * 
 * @author Grant_ | www.rune-server.ee/members/grant_ | 12/5/19
 *
 */
public class Kraken {

	public static final int KRAKEN_ID = 494;
	private static final int MAX_DAMAGE = 30;

	public static void init(Player player) {
		if (player.getSlayer().getTask().isEmpty() ||
				!player.getSlayer().getTask().get().getPrimaryName().contains("kraken") && player.getRights().getPrimary() != Right.OWNER) {
			player.sendMessage("You must have an active kraken task in order to do this.");
			return;
		}

		KrakenInstance instance = new KrakenInstance(player, Boundary.KRAKEN_BOSS_ROOM);
		player.getPA().movePlayer(new Coordinate(2280, 10022, instance.getHeight()));
		NPC npc = NPCSpawning.spawnNpc(player, KRAKEN_ID, 2279, 10035, instance.getHeight(), -1, MAX_DAMAGE, true, false);
		npc.getBehaviour().setRespawnWhenPlayerOwned(true);
		instance.add(npc);
		instance.add(player);
	}

}
