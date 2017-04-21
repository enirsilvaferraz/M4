package com.system.m4.views.components.dialogs.list;

import java.io.Serializable;

public class ItemList implements Serializable {

    private String name;

    public ItemList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}