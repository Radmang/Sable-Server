package io.sable.model;

import io.sable.util.Misc;

public enum SpellBook {
    MODERN, ANCIENT, LUNAR
    ;

    @Override
    public String toString() {
        return Misc.formatPlayerName(name().toLowerCase());
    }
}
