package io.sable.content.minigames.barrows;

import io.sable.model.items.GameItem;
import io.sable.util.Misc;

public class RewardItem extends GameItem {

	private int minAmount;
	private int maxAmount;
	private final RewardLevel rarity;

	public RewardItem(int id, int minAmount, int maxAmount, RewardLevel rarity) {
		super(id);
		this.minAmount = minAmount;
		this.maxAmount = maxAmount;
		this.rarity = rarity;
	}

	@Override
	public int getAmount() {
		return Misc.random(maxAmount - minAmount) + minAmount;
	}

	int getMinAmount() {
		return minAmount;
	}

	void setMinAmount(int minAmount) {
		this.minAmount = minAmount;
	}

	int getMaxAmount() {
		return maxAmount;
	}

	void setMaxAmount(int maxAmount) {
		this.maxAmount = maxAmount;
	}

	RewardLevel getRarityLevel() {
		return rarity;
	}

}
