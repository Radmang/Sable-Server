package io.sable.content.minigames.raids;

import io.sable.content.party.PartyFormAreaController;
import io.sable.content.party.PlayerParty;
import io.sable.model.entity.player.Boundary;

import java.util.Set;

public class CoxPartyFormAreaController extends PartyFormAreaController {

    @Override
    public String getKey() {
        return CoxParty.TYPE;
    }

    @Override
    public Set<Boundary> getBoundaries() {
        return Set.of(Boundary.RAIDS_LOBBY_ENTRANCE);
    }

    @Override
    public PlayerParty createParty() {
        return new CoxParty();
    }
}
