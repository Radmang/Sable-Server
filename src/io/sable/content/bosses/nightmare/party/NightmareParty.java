package io.sable.content.bosses.nightmare.party;

import io.sable.content.party.PartyInterface;
import io.sable.content.party.PlayerParty;
import io.sable.model.entity.player.Player;

public class NightmareParty extends PlayerParty {

    public static final String TYPE = "Nightmare Party";

    public NightmareParty() {
        super(TYPE, 100);
    }

    @Override
    public boolean canJoin(Player invitedBy, Player invited) {
        return true;
    }

    @Override
    public void onJoin(Player player) {
        PartyInterface.refreshOnJoinOrLeave(player, this);
    }

    @Override
    public void onLeave(Player player) {
        PartyInterface.refreshOnJoinOrLeave(player, this);
    }
}
