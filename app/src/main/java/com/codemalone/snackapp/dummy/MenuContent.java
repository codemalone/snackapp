package com.codemalone.snackapp.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public static final List<MenuItem> ITEMS = new ArrayList<MenuItem>();

    static {
        addItem(new MenuItem("French fries", "Veggie"));
        addItem(new MenuItem("Veggieburger", "Veggie"));
        addItem(new MenuItem("Carrots", "Veggie"));
        addItem(new MenuItem("Apple", "Veggie"));
        addItem(new MenuItem("Banana", "Veggie"));
        addItem(new MenuItem("Milkshake", "Veggie"));
        addItem(new MenuItem("Cheeseburger", "Non-Veggie"));
        addItem(new MenuItem("Hamburger", "Non-Veggie"));
        addItem(new MenuItem("Hot dog", "Non-Veggie"));
    }

    private static void addItem(MenuItem item) {
        ITEMS.add(item);
        //ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class MenuItem {
        public final String name;
        public final String category;

        public MenuItem(String name, String category) {
            this.name = name;
            this.category = category;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
