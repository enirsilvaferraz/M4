package com.system.m4.businness.dtos;

/**
 * Created by eferraz on 30/04/17.
 * For M4
 */

public class TagDTO implements DTOInterface {

    private String name;

    public TagDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
