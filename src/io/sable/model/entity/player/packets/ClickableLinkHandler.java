package io.sable.model.entity.player.packets;

import io.sable.Server;
import io.sable.model.entity.npc.NPC;
import io.sable.model.entity.npc.NPCHandler;
import io.sable.model.entity.player.PacketType;
import io.sable.model.entity.player.Player;

public class ClickableLinkHandler implements PacketType {

    @Override
    public void processPacket(Player player, int type, int size) {
        int npcIndex = player.getInStream().readUnsignedWord();
        if (npcIndex < 0 || npcIndex > NPCHandler.npcs.length)
            return;
        NPC npc = NPCHandler.npcs[npcIndex];
        if (npc  == null)
            return;

        if (npc.getDefinition().getCombatLevel() > 0) {
            player.sendMessage("Opening drops for: "+npc.getName()+"...");
            Server.getDropManager().openForPacket(player, npc.getNpcId());
        }
    }
}
