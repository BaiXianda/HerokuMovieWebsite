package com.xiandabai.moviewebsite.exceptions;

public class GroupIDExcepationResponse {

    private String GroupID;

    public GroupIDExcepationResponse(String groupID) {
        GroupID = groupID;
    }

    public String getGroupID() {
        return GroupID;
    }

    public void setGroupID(String groupID) {
        GroupID = groupID;
    }
}
