package com.system.m4.views.vos;

import android.os.Parcel;
import android.support.annotation.NonNull;

/**
 * Created by eferraz on 30/04/17.
 * For M4
 */

public class TagSummaryVO implements VOInterface<TagSummaryVO>, VOItemListInterface {

    public static final Creator<TagSummaryVO> CREATOR = new Creator<TagSummaryVO>() {
        @Override
        public TagSummaryVO createFromParcel(Parcel source) {
            return new TagSummaryVO(source);
        }

        @Override
        public TagSummaryVO[] newArray(int size) {
            return new TagSummaryVO[size];
        }
    };
    private String key;
    private String name;
    private Double value;

    public TagSummaryVO() {
    }

    public TagSummaryVO(String key, String name, Double price) {
        this.key = key;
        this.name = name;
        this.value = price;
    }

    protected TagSummaryVO(Parcel in) {
        this.key = in.readString();
        this.name = in.readString();
        this.value = (Double) in.readValue(Double.class.getClassLoader());
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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagSummaryVO tagVO = (TagSummaryVO) o;

        return key != null ? key.equals(tagVO.key) : tagVO.key == null;

    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }

    @Override
    public int compareTo(@NonNull TagSummaryVO o) {
        return this.name.compareTo(o.getName());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeValue(this.value);
    }
}
