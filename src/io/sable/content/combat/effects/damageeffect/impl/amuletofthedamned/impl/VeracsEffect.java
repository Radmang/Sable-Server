package io.sable.content.combat.effects.damageeffect.impl.amuletofthedamned.impl;

import io.sable.content.combat.Damage;
import io.sable.content.combat.effects.damageeffect.impl.amuletofthedamned.AmuletOfTheDamnedEffect;
import io.sable.model.entity.Entity;
import io.sable.model.entity.player.Player;
import io.sable.model.items.EquipmentSet;

/**
 * @author Arthur Behesnilian 1:10 PM
 */
public class VeracsEffect implements AmuletOfTheDamnedEffect {

    /**
     * The singleton instance of the Amulet of the damned effect for Veracs
     */
    public static final AmuletOfTheDamnedEffect INSTANCE = new VeracsEffect();

    @Override
    public boolean hasExtraRequirement(Player player) {
        return EquipmentSet.VERAC.isWearingBarrows(player);
    }

    @Override
    public void useEffect(Player player, Entity other, Damage damage) {
        // Effects happen inline elsewhere
    }
}
