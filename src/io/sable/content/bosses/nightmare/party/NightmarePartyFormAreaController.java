package io.sable.content.bosses.nightmare.party;

import io.sable.content.bosses.nightmare.NightmareConstants;
import io.sable.content.party.PartyFormAreaController;
import io.sable.content.party.PlayerParty;
import io.sable.model.entity.player.Boundary;

import java.util.Set;

public class NightmarePartyFormAreaController extends PartyFormAreaController {

    @Override
    public String getKey() {
        return NightmareParty.TYPE;
    }

    @Override
    public Set<Boundary> getBoundaries() {
        return Set.of(NightmareConstants.LOBBY_BOUNDARY);
    }

    @Override
    public PlayerParty createParty() {
        return new NightmareParty();
    }
}
