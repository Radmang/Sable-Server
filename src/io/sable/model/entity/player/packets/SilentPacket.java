package io.sable.model.entity.player.packets;

import io.sable.model.entity.player.PacketType;
import io.sable.model.entity.player.Player;

/**
 * Slient Packet
 **/
public class SilentPacket implements PacketType {

	@Override
	public void processPacket(Player c, int packetType, int packetSize) {

	}
}
