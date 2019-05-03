package com.codemalone.snackapp.dummy;

public class Item {
    public final String name;
    public final String category;

    public Item(String name, String category) {
        this.name = name;
        this.category = category;
    }

    @Override
    public String toString() {
        return name;
    }
}
