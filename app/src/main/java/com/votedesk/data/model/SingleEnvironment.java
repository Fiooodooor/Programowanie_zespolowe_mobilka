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
    private Integer Owner;
    private String CoverUri;
    private ArrayList<SingleProject> ProjectList;

    public SingleEnvironment(JSONObject EnvSingle) throws JSONException {
        setId(EnvSingle.getInt("id"));
        setOwner(EnvSingle.getInt("id"));
        setName(EnvSingle.getString("environment_name"));
        setCoverUri(EnvSingle.optString("cover", ""));
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
    public SingleEnvironment(Integer Id, String Name, Integer Owner, String CoverUri, ArrayList<SingleProject> ProjectList) {
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
    public void setOwner(Integer owner) {
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

    public Integer getOwner() {
        return Owner;
    }

    public String getCoverUri() {
        return CoverUri;
    }

    public ArrayList<SingleProject> getProjectList() {
        return ProjectList;
    }
}
