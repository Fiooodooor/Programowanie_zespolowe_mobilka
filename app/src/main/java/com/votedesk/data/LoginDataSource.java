package com.votedesk.data;

import android.os.AsyncTask;

import com.votedesk.data.model.LoggedInUser;

import org.json.JSONObject;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {
        try {
            LoginDataAsynchEndpoint apiLoginEndpoint = new LoginDataAsynchEndpoint(username, password);
            String loginResult = apiLoginEndpoint.execute();
            if(loginResult.isEmpty())
                throw new IOException("Error logging in");
            JSONObject response = new JSONObject(loginResult);
            if (response.has("token")) {
                LoggedInUser tempUser = new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            username, response.getString("token"));
                return new Result.Success<>(tempUser);
            }
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
        return new Result.Error(new IOException("Wrong credentials"));
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
