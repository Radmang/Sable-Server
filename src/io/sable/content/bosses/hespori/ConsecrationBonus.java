package io.sable.content.bosses.hespori;

import io.sable.content.QuestTab;
import io.sable.content.wogw.Wogw;
import io.sable.model.entity.player.Player;
import io.sable.model.entity.player.PlayerHandler;

import java.util.concurrent.TimeUnit;

public class ConsecrationBonus implements HesporiBonus {
    @Override
    public void activate(Player player) {
        Wogw.PC_POINTS_TIMER += TimeUnit.HOURS.toMillis(1) / 600;
        PlayerHandler.executeGlobalMessage("@bla@[@gre@Hespori@bla@] @blu@" + player.getDisplayNameFormatted() + " @bla@sprouted the Consecration and it is granting 1 hr of +5 PC points!");
        QuestTab.updateAllQuestTabs();
    }

    @Override
    public void deactivate() {
        updateObject(false);
        Hespori.activeConsecrationSeed = false;
        Hespori.CONSECRATION_TIMER = 0;
    }

    @Override
    public boolean canPlant(Player player) {
        return true;
    }

    @Override
    public HesporiBonusPlant getPlant() {
        return HesporiBonusPlant.CONSECRATION;
    }
}
