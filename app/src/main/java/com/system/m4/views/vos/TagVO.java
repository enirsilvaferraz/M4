package com.system.m4.views.vos;

import android.os.Parcel;

import com.system.m4.businness.dtos.TagDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eferraz on 30/04/17.
 * For M4
 */

public class TagVO implements VOInterface {

    private String name;

    public TagVO(String name) {
        this.name = name;
    }

    public static List<TagVO> asList(List<TagDTO> dtolist) {
        List<TagVO> volist = new ArrayList<>();
        for (TagDTO dto : dtolist) {
            volist.add(new TagVO(dto.getName()));
        }
        return volist;
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

    protected TagVO(Parcel in) {
        this.name = in.readString();
    }

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
}
