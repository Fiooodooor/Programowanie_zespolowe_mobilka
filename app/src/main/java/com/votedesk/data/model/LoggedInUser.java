package com.votedesk.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private String tokenId;

    public LoggedInUser() {}
    public LoggedInUser(String userId, String displayName, String tokenId) {
        this.userId = userId;
        this.displayName = displayName;
        this.tokenId = tokenId;
    }

    public String getUserId() {
        return userId;
    }
    public String getDisplayName() {
        return displayName;
    }
    public String getTokenId() { return tokenId; }
    public String getWrappedTokenId() { return "Token " + getTokenId(); }
}
