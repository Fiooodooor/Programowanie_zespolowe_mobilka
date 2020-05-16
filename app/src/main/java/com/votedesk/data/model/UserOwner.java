package com.votedesk.data.model;

import org.json.JSONException;
import org.json.JSONObject;

public class UserOwner {
    private int userId;
    private String userName;
    private String firstName;
    public UserOwner(JSONObject userObject) throws JSONException {
        setUserId(userObject.getInt("id"));
        setUserName(userObject.getString("username"));
        setFirstName(userObject.getString("first_name"));
    }
    public UserOwner(int userId) {
        setUserId(userId);
        setUserName("Unknown");
        setFirstName("Unknown");
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
