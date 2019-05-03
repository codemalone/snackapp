package com.codemalone.snackapp.dummy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class Menu implements Serializable {

    /**
     * An array of sample (dummy) items.
     */
    public final List<Item> items;

    public Menu() {
        items = new ArrayList<>();
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

    public void addItem(final Item theItem) {
        items.add(theItem);
    }

}
