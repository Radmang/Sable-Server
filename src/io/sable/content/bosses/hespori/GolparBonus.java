package io.sable.content.bosses.hespori;

import io.sable.content.QuestTab;
import io.sable.model.entity.player.Player;
import io.sable.model.entity.player.PlayerHandler;

import java.util.concurrent.TimeUnit;

public class GolparBonus implements HesporiBonus {
    @Override
    public void activate(Player player) {
        Hespori.activeGolparSeed = true;
        Hespori.GOLPAR_TIMER += TimeUnit.HOURS.toMillis(1) / 600;
        PlayerHandler.executeGlobalMessage("@bla@[@gre@Hespori@bla@] @red@" + player.getDisplayNameFormatted() + " @bla@planted a Golpar seed which" +
                " granted @red@1 hour of 2x bonus loot including:");
        PlayerHandler.executeGlobalMessage("@red@                   Crystal keys, Coin bags, Resource boxes and Clues!");
        QuestTab.updateAllQuestTabs();
    }


    @Override
    public void deactivate() {
        updateObject(false);
        Hespori.activeGolparSeed = false;
        Hespori.GOLPAR_TIMER = 0;

    }

    @Override
    public boolean canPlant(Player player) {

        return true;
    }

    @Override
    public HesporiBonusPlant getPlant() {
        return HesporiBonusPlant.GOLPAR;
    }
}
