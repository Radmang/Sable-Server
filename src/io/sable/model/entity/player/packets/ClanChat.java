package io.sable.model.entity.player.packets;

import io.sable.model.entity.player.PacketType;
import io.sable.model.entity.player.Player;
import io.sable.util.Misc;

/**
 * Chat
 **/
public class ClanChat implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {
		String textSent = Misc.longToPlayerName2(c.getInStream().readLong());
		textSent = textSent.replaceAll("_", " ");
	}
}