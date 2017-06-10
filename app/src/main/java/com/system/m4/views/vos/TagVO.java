package com.system.m4.views.vos;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.system.m4.repository.dtos.TagDTO;

/**
 * Created by eferraz on 30/04/17.
 * For M4
 */

public class TagVO implements VOInterface<TagVO>{

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

    public TagVO(TagDTO dto) {
        this.key = dto.getKey();
        this.name = dto.getName();
    }

    public TagVO() {
    }

    protected TagVO(Parcel in) {
        this.name = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
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
}
