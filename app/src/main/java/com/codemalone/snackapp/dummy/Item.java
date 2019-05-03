package com.codemalone.snackapp.dummy;

public class Item {
    public final String name;
    public final String category;
    public boolean isSelected;
    public boolean isVisible;

    public Item(String name, String category) {
        this.name = name;
        this.category = category;
        this.isSelected = false;
        this.isVisible = true;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Item)) {
            return false;
        } else {
            Item other = (Item) obj;
            return (this.name == other.name);
        }
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}
