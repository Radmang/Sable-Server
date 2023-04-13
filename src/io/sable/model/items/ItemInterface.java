package io.sable.model.items;

import io.sable.model.definitions.ItemDef;

public interface ItemInterface {

    default ItemDef getDef() {
        return ItemDef.forId(getId());
    }

    int getId();

    int getAmount();

}
