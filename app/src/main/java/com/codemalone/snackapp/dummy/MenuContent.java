package com.codemalone.snackapp.dummy;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class MenuContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<Item> ITEMS = new ArrayList<Item>();

    static {
        addItem(new Item("French fries", "Veggie"));
        addItem(new Item("Veggieburger", "Veggie"));
        addItem(new Item("Carrots", "Veggie"));
        addItem(new Item("Apple", "Veggie"));
        addItem(new Item("Banana", "Veggie"));
        addItem(new Item("Milkshake", "Veggie"));
        addItem(new Item("Cheeseburger", "Non-Veggie"));
        addItem(new Item("Hamburger", "Non-Veggie"));
        addItem(new Item("Hot dog", "Non-Veggie"));
    }

    private static void addItem(Item item) {
        ITEMS.add(item);
        //ITEM_MAP.put(item.id, item);
    }
}
