package com.system.m4.views.components.dialogs.list;

import android.os.Parcel;
import android.os.Parcelable;

import com.system.m4.views.vos.VOInterface;

import java.util.ArrayList;
import java.util.List;

public class ItemList implements Parcelable {

    public static final Parcelable.Creator<ItemList> CREATOR = new Parcelable.Creator<ItemList>() {
        @Override
        public ItemList createFromParcel(Parcel source) {
            return new ItemList(source);
        }

        @Override
        public ItemList[] newArray(int size) {
            return new ItemList[size];
        }
    };

    private Integer id;

    private String name;

    public ItemList(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ItemList(String name) {
        this.name = name;
    }

    protected ItemList(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
    }

    public static List<ItemList> asList(List<? extends VOInterface> listString) {
        List<ItemList> list = new ArrayList<>();
        for (VOInterface vo : listString) {
            list.add(new ItemList(vo.getName()));
        }
        return list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}