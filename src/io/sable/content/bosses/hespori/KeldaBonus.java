package io.sable.content.bosses.hespori;

import io.sable.content.QuestTab;
import io.sable.model.entity.player.Player;
import io.sable.model.entity.player.PlayerHandler;

import java.util.concurrent.TimeUnit;

public class KeldaBonus implements HesporiBonus {
    @Override
    public void activate(Player player) {
        Hespori.activeKeldaSeed = true;
        Hespori.KELDA_TIMER += TimeUnit.HOURS.toMillis(1) / 600;
        PlayerHandler.executeGlobalMessage("@bla@[@gre@Hespori@bla@] @red@" + player.getDisplayNameFormatted() + " @bla@planted a Kelda seed which" +
                " granted @red@1 hour of 2x Larren's keys.");
        QuestTab.updateAllQuestTabs();
    }


    @Override
    public void deactivate() {
        updateObject(false);
        Hespori.activeKeldaSeed = false;
        Hespori.KELDA_TIMER = 0;

    }

    @Override
    public boolean canPlant(Player player) {

        return true;
    }

    @Override
    public HesporiBonusPlant getPlant() {
        return HesporiBonusPlant.KELDA;
    }
}
