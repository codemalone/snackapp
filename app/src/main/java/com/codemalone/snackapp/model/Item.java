package com.codemalone.snackapp.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Represents a menu item to display in the RecyclerView.
 */
public class Item implements Parcelable {
    /* key names for Parcelable storage. */
    private static String ARGS_NAME = "name";
    private static String ARGS_CATEGORY = "category";
    private static String ARGS_SELECTED = "selected";
    private static String ARGS_VISIBLE = "visible";

    /* Instance variables */
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

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Item) {
            Item other = (Item) obj;
            return (this.name.equals(other.name));
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle fields = new Bundle();
        fields.putString(ARGS_NAME, this.name);
        fields.putString(ARGS_CATEGORY, this.category);
        fields.putBoolean(ARGS_SELECTED, this.isSelected);
        fields.putBoolean(ARGS_VISIBLE, this.isVisible);
        dest.writeBundle(fields);
    }

    /**
     * Parcelable is used to store and retrieve object instance state
     * before and after an Android system event.
     */
    public static final Parcelable.Creator<Item> CREATOR  = new Parcelable.Creator<Item>() {
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    private Item(Parcel in) {
        Bundle fields = in.readBundle();
        name = fields.getString(ARGS_NAME);
        category = fields.getString(ARGS_CATEGORY);
        isSelected = fields.getBoolean(ARGS_SELECTED);
        isVisible = fields.getBoolean(ARGS_VISIBLE);
    }
}
