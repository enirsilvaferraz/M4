package com.system.m4.repository.dtos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eferraz on 30/04/17.
 * For M4
 */

public class TagDTO extends DTOAbs {

    @Expose
    @SerializedName("key")
    private String key;

    @Expose
    @SerializedName("name")
    private String name;

    public TagDTO() {
        // Nothing to do
    }

    public TagDTO(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagDTO tagDTO = (TagDTO) o;

        return key != null ? key.equals(tagDTO.key) : tagDTO.key == null;

    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }
}
