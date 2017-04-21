package com.system.m4.views.components.dialogs.list;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemList implements Serializable {

    private String name;

    public ItemList(String name) {
        this.name = name;
    }

    public static List<ItemList> asList(List<String> listString) {

        List<ItemList> list = new ArrayList<>();
        for (String string : listString) {
            list.add(new ItemList(string));
        }
        return list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}