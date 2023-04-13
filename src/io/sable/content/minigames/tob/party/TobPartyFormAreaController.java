package io.sable.content.minigames.tob.party;

import io.sable.content.minigames.tob.TobConstants;
import io.sable.content.party.PartyFormAreaController;
import io.sable.content.party.PlayerParty;
import io.sable.model.entity.player.Boundary;

import java.util.Set;

public class TobPartyFormAreaController extends PartyFormAreaController {

    @Override
    public String getKey() {
        return TobParty.TYPE;
    }

    @Override
    public Set<Boundary> getBoundaries() {
        return Set.of(TobConstants.TOB_LOBBY);
    }

    @Override
    public PlayerParty createParty() {
        return new TobParty();
    }
}
