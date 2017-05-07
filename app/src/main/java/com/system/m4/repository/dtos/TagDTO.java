package com.system.m4.repository.dtos;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.system.m4.views.vos.TagVO;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by eferraz on 30/04/17.
 * For M4
 */

public class TagDTO extends DTOAbs {

    @SerializedName("key")
    private String key;

    @SerializedName("name")
    private String name;

    public TagDTO() {
        // Nothing to do
    }

    public TagDTO(TagVO vo) {
        this.key = vo.getKey();
        this.name = vo.getName();
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
    public Map<String, String> getMapUpdate() {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        return gson.fromJson(gson.toJson(this), type);
    }
}
