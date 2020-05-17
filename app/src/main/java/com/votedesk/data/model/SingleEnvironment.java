package com.votedesk.data.model;

import com.votedesk.data.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SingleEnvironment {
    private Integer Id;
    private String Name;
    private UserOwner Owner;
    private String CoverUri;
    private ArrayList<SingleProject> ProjectList;

    public SingleEnvironment(JSONObject EnvSingle) throws JSONException {
        setId(EnvSingle.getInt("id"));
        setOwner(new UserOwner(EnvSingle.getJSONObject("owner")));
        setName(EnvSingle.getString("environment_name"));
        setCoverUri(EnvSingle.optString("cover_image", ""));
        setProjectList(new ArrayList<SingleProject>());
        JSONArray projectList = EnvSingle.getJSONArray("projects");
        for(int i=0; i<projectList.length(); i++) {
            JSONObject projSingle = projectList.getJSONObject(i);
            if(projSingle != null) {
                SingleProject projNode = new SingleProject(projSingle);
                ProjectList.add(projNode);
            }
        }
    }
    public SingleEnvironment(Integer Id, String Name, UserOwner Owner, String CoverUri, ArrayList<SingleProject> ProjectList) {
        this.Id = Id;
        this.Name = Name;
        this.Owner = Owner;
        this.CoverUri = CoverUri;
        this.ProjectList = ProjectList;
    }

    public void setId(Integer id) {
        Id = id;
    }
    public void setName(String name) {
        Name = name;
    }
    public void setOwner(UserOwner owner) {
        Owner = owner;
    }
    public void setCoverUri(String coverUri) {
        CoverUri = coverUri;
    }
    public void setProjectList(ArrayList<SingleProject> projectList) {
        ProjectList = projectList;
    }

    public Integer getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public UserOwner getOwner() {
        return Owner;
    }

    public String getCoverUri() {
        return CoverUri;
    }

    public ArrayList<SingleProject> getProjectList() {
        return ProjectList;
    }
}
