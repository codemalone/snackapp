package com.codemalone.snackapp.dummy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Order implements Serializable {
    public List<Item> items;

    public Order() {
        items = new ArrayList<>();
    }

    public Collection<Item> getItems() {
        return items;
    }

}
