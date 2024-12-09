package com.ffa.back.dto;

public class GroupMemberRequestDTO {

    private String email;

    public GroupMemberRequestDTO() {
    }

    public GroupMemberRequestDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
