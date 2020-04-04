package com.votedesk.data;

import com.votedesk.data.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private RestApiCall dataSource;
    private boolean loginInProgress;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(RestApiCall dataSource) {
        this.dataSource = dataSource;
        this.loginInProgress = false;
    }

    public static LoginRepository getInstance(RestApiCall dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }
    public boolean isInProgress() {
        if(isLoggedIn()) {
            return false;
        }
        return loginInProgress;
    }

    public void logout() {
        user = null;
        //dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String username, String password) {
        loginInProgress = true;
        Result<LoggedInUser> result = dataSource.UserLogin(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        loginInProgress = false;
        return result;
    }
}
