package io.sable.model.shops;

import io.sable.model.items.NamedItem;
import io.sable.util.ItemConstants;

public class NamedShopItem extends NamedItem {

    private int price;

    public ShopItem toShopItem(ItemConstants itemConstants) {
        return new ShopItem(getId(itemConstants), getAmount(), price);
    }

    public NamedShopItem() {
    }

    public int getPrice() {
        return price;
    }
}
