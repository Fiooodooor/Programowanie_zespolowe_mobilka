package com.votedesk.data;

import com.votedesk.R;
import com.votedesk.data.model.LoggedInUser;

import org.json.JSONObject;

import java.io.IOException;

public class RestApiCall {
    public Result<LoggedInUser> UserLogin(String username, String password) {
        UserLoginApi restApi = new UserLoginApi(username, password);
        return restApi.Start();
    }

    final public class UserLoginApi extends AsynchEndpointComm {
        private String username;
        private String password;

        UserLoginApi(String username, String password) {
            super("/auth/", requestMethods.POST);
            this.username = username;
            this.password = password;
        }
        final protected void CreatePayloadJsonObject() {
            try {
                JSONObject tmpPayload = new JSONObject();
                tmpPayload.put("username", username);
                tmpPayload.put("password", password);
                CreatePayloadJsonObject(tmpPayload);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        final public Result<LoggedInUser> Start() {
            try {
                CreatePayloadJsonObject();
                String loginResult = this.execute();
                if(loginResult.isEmpty())
                    throw new IOException("Login data error. Struct empty");
                JSONObject response = new JSONObject(loginResult);

                if (response.has("token")) {
                    String tokenStr = response.getString("token");
                    AsynchEndpointComm.SetToken(tokenStr);
                    LoggedInUser activeUser = new LoggedInUser("1", username, tokenStr);
                    return new Result.Success<LoggedInUser>(activeUser);
                }
            } catch(Exception e) {
                return new Result.Error(new IOException("Error logging in", e));
            }
            return new Result.Error(new IOException("Wrong credentials"));
        }
    }
}
