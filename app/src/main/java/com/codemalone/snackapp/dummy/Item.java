package com.codemalone.snackapp.dummy;

public class Item {
    public final String name;
    public final String category;
    public boolean isChecked;

    public Item(String name, String category) {
        this.name = name;
        this.category = category;
        this.isChecked = false;
    }

    @Override
    public String toString() {
        return name;
    }
}
