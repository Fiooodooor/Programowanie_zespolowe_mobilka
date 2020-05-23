package com.votedesk.data;
//"/api/environments/"
import android.content.res.Resources;

import com.votedesk.R;
import com.votedesk.data.model.LoggedInUser;
import com.votedesk.data.model.SingleEnvironment;
import com.votedesk.data.model.SingleProject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RestApiCall {
    public Result<LoggedInUser> UserLogin(String username, String password) {
        UserLoginApi restApi = new UserLoginApi(username, password);
        return restApi.Start();
    }
    public Result<ArrayList<SingleEnvironment>> ListEnvironments() {
        ListEnvironmentsApi restApi = new ListEnvironmentsApi();
        return restApi.Start();
    }
    public Result<SingleProject> ProjectDetails(String projectId) {
        ProjectDetailsApi restApi = new ProjectDetailsApi(projectId);
        return restApi.Start();
    }
    public Result<Boolean> ProjectVote(String projectId, int voteMark) {
        ProjectVoteApi restApi = new ProjectVoteApi(projectId, voteMark);
        return restApi.Start();
    }

    final public class ProjectVoteApi extends AsynchEndpointComm {
        private int voteMark;
        ProjectVoteApi(String projectId, int voteMark) {
            super("projects/" + projectId + "/vote/", requestMethods.POST);
            this.voteMark = voteMark;
        }
        final protected void CreatePayloadJsonObject() {
            try {
                JSONObject tmpPayload = new JSONObject();
                tmpPayload.put("rate", this.voteMark);
                CreatePayloadJsonObject(tmpPayload);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        final public Result<Boolean> Start() {
            try {
                CreatePayloadJsonObject();
                String voteResult = this.execute();
                if(voteResult.isEmpty())
                    throw new IOException(Resources.getSystem().getString(R.string.error_project_details));
                JSONObject voteResultJson = new JSONObject(voteResult);
                if(voteResultJson.optInt("result", 0) > 0)
                    return new Result.Success<Boolean>(true);
                return new Result.Success<Boolean>(false);
            } catch(Exception e) {
                return new Result.Error(new IOException(Resources.getSystem().getString(R.string.error_project_details_e), e));
            }
        }
    }

    final public class ProjectDetailsApi extends AsynchEndpointComm {
        ProjectDetailsApi(String projectId) {
            super("projects/" + projectId, requestMethods.GET);
        }
        final protected void CreatePayloadJsonObject() { }

        final public Result<SingleProject> Start() {
            try {
                String EnvString = this.execute();
                if(EnvString.isEmpty())
                    throw new IOException(Resources.getSystem().getString(R.string.error_project_details));
                JSONObject jsonProject = new JSONObject(EnvString);
                return new Result.Success<SingleProject>(new SingleProject(jsonProject, true));
            } catch(Exception e) {
                return new Result.Error(new IOException(Resources.getSystem().getString(R.string.error_project_details_e), e));
            }
        }
    }

    final public class ListEnvironmentsApi extends AsynchEndpointComm {
        ListEnvironmentsApi() {
            super("/environments/", requestMethods.GET);
        }
        final protected void CreatePayloadJsonObject() {
        }

        final public Result<ArrayList<SingleEnvironment>> Start() {
            try {
                String EnvString = this.execute();
                if(EnvString.isEmpty())
                    throw new IOException(Resources.getSystem().getString(R.string.error_project_list));
                JSONArray EnvJsonList = new JSONArray(EnvString);

                ArrayList<SingleEnvironment> userEnvList = new ArrayList<SingleEnvironment>();
                for(int i=0; i<EnvJsonList.length(); i++) {
                    JSONObject EnvSingle = EnvJsonList.getJSONObject(i);
                    SingleEnvironment node = new SingleEnvironment(EnvSingle);
                    userEnvList.add(node);
                    //(Integer Id, String Name, String Owner, String CoverUri, List<SingleProject> ProjectList)
                }
                return new Result.Success<ArrayList<SingleEnvironment>>(userEnvList);

            } catch(Exception e) {
                return new Result.Error(new IOException(Resources.getSystem().getString(R.string.error_project_list_e), e));
            }
        }
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
