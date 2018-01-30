package com.system.m4.views.vos;

import android.os.Parcel;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.Nullable;

/**
 * Created by eferraz on 30/04/17.
 * For M4
 */

public class TagVO implements VOInterface<TagVO> {

    public static final Creator<TagVO> CREATOR = new Creator<TagVO>() {
        @Override
        public TagVO createFromParcel(Parcel source) {
            return new TagVO(source);
        }

        @Override
        public TagVO[] newArray(int size) {
            return new TagVO[size];
        }
    };
    private String key;
    private String name;
    private String parentName;

    public TagVO() {
    }

    public TagVO(String key, String parentName, String name) {
        this.key = key;
        this.name = name;
        this.parentName = parentName;
    }

    protected TagVO(Parcel in) {
        this.key = in.readString();
        this.name = in.readString();
        this.parentName = in.readString();
    }

    public TagVO(@Nullable String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagVO tagVO = (TagVO) o;

        return key != null ? key.equals(tagVO.key) : tagVO.key == null;

    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }

    @Override
    public int compareTo(@NonNull TagVO o) {
        return this.name.compareTo(o.getName());
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.parentName);
    }
}
