package com.ffa.back.dto;

public class GroupCreateRequestDTO {
    private String name;

    public GroupCreateRequestDTO() {
    }

    public GroupCreateRequestDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
