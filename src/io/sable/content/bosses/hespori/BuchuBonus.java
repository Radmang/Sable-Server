package io.sable.content.bosses.hespori;

import io.sable.content.QuestTab;
import io.sable.model.entity.player.Player;
import io.sable.model.entity.player.PlayerHandler;

import java.util.concurrent.TimeUnit;

public class BuchuBonus implements HesporiBonus {
    @Override
    public void activate(Player player) {
        Hespori.activeBuchuSeed = true;
        Hespori.BUCHU_TIMER += TimeUnit.HOURS.toMillis(1) / 600;
        PlayerHandler.executeGlobalMessage("@bla@[@gre@Hespori@bla@] @red@" + player.getDisplayNameFormatted() + " @bla@planted a Buchu seed which" +
                " granted @red@1 hour of 2x Boss points.");
        QuestTab.updateAllQuestTabs();
    }


    @Override
    public void deactivate() {
        updateObject(false);
        Hespori.activeBuchuSeed = false;
        Hespori.BUCHU_TIMER = 0;

    }

    @Override
    public boolean canPlant(Player player) {

        return true;
    }

    @Override
    public HesporiBonusPlant getPlant() {
        return HesporiBonusPlant.BUCHU;
    }
}
