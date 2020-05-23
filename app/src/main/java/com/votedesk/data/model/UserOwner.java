package com.votedesk.data.model;

import org.json.JSONException;
import org.json.JSONObject;

public class UserOwner {
    private int userId;
    private String userName;
    private String firstName;
    private String avatar;

    public UserOwner(JSONObject userObject) throws JSONException {
        setUserId(userObject.getInt("id"));
        setUserName(userObject.getString("username"));
        setFirstName(userObject.getString("first_name"));
        setAvatar(userObject.getString("avatar"));
    }
    public UserOwner(int userId) {
        setUserId(userId);
        setUserName("Unknown");
        setFirstName("Unknown");
        setAvatar("");
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        if(avatar.contains("127.0.0.1:8000")) {
            this.avatar = avatar.replace("127.0.0.1:8000", "ec2-3-9-170-154.eu-west-2.compute.amazonaws.com");
        }
        else if(avatar.compareTo("null")==0) {
            this.avatar = "";
        }
        else {
            this.avatar = "http://ec2-3-9-170-154.eu-west-2.compute.amazonaws.com";
            this.avatar += avatar;
        }
    }
}
