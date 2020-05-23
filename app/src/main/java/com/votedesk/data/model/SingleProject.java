package com.votedesk.data.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SingleProject {
    private Integer Id;
    private String  Name;
    private UserOwner Owner;
    private String  CoverUri;
    private String  Status;
    private String  Category;
    private String  Content;
    private String Comments;
    private Integer Votes;
    private Integer Mark;
    private Integer Environment;
    private String Environment_name;
    private boolean Can_vote;

    public SingleProject(JSONObject sProject)  throws JSONException {
        this(sProject, false);
    }
    public SingleProject(JSONObject sProject, boolean sExtend)  throws JSONException {
        setId(sProject.getInt("id"));
        JSONObject owner = sProject.optJSONObject("owner");
        if(owner == null) {
            setOwner(new UserOwner(sProject.getInt("owner")));
        } else {
            setOwner(new UserOwner(owner));
        }
        setName(sProject.getString("project_name"));
        setContent(sProject.optString("project_content", "DataError"));
        setCoverUri(sProject.optString("cover_image", ""));
        setMark(0);
        if(sExtend) {
            setEnvironment(sProject.optInt("environment", 0));
            setEnvironment_name(sProject.optString("environment_name", "no name"));
            setStatus(sProject.optString("project_status", "DataError"));
            setCategory(sProject.optString("project_category", "DataError"));
            setVotes(sProject.optInt("vote_average", 0));
            setCan_vote(sProject.optBoolean("can_vote", false));
            setComments("some comments");
        }
        else {
            setEnvironment(0);
            setEnvironment_name("not loaded");
            setStatus("not loaded");
            setCategory("not loaded");
            setComments("not loaded");
            setCan_vote(false);
            setVotes(0);
        }
    }

    SingleProject(SingleProject cp) {
        this.setId(cp.getId());
        this.setName(cp.getName());
        this.setOwner(cp.getOwner());
        this.setCoverUri(cp.getCoverUri());
        this.setStatus(cp.getStatus());
        this.setCategory(cp.getCategory());
        this.setContent(cp.getContent());
        this.setComments(cp.getComments());
        this.setVotes(cp.getVotes());
        this.setMark(cp.getMark());
        this.setEnvironment(cp.getEnvironment());
        this.setEnvironment_name(cp.getEnvironment_name());
    }

    public boolean isCan_vote() {
        return Can_vote;
    }

    public void setCan_vote(boolean can_vote) {
        Can_vote = can_vote;
    }

    public Integer getEnvironment() {
        return Environment;
    }

    public void setEnvironment(Integer environment) {
        Environment = environment;
    }

    public String getEnvironment_name() {
        return Environment_name;
    }

    public void setEnvironment_name(String environment_name) {
        Environment_name = environment_name;
    }

    /**
     *
     * @param Id
     * The id
     */
    public void setId(Integer Id) { this.Id = Id;    }
    /**
     *
     * @param Name
     * The name
     */
    public void setName(String Name) { this.Name = Name;    }
    /**
     *
     * @param Owner
     * The owner
     */
    public void setOwner(UserOwner Owner) { this.Owner = Owner;    }
    /**
     *
     * @param coverUri
     * The cover_uri
     */
    public void setCoverUri(String coverUri) {
        if(coverUri.contains("127.0.0.1:8000")) {
            CoverUri = coverUri.replace("127.0.0.1:8000", "ec2-3-9-170-154.eu-west-2.compute.amazonaws.com");
        }
        else if(coverUri.compareTo("null")==0) {
            CoverUri = "";
        }
        else {
            CoverUri = "http://ec2-3-9-170-154.eu-west-2.compute.amazonaws.com";
            CoverUri += coverUri;
        }
    }
    /**
     *
     * @param Votes
     * The votes
     */
    public void setVotes(Integer Votes) { this.Votes = Votes;    }
    /**
     *
     * @param Mark
     * The mark
     */
    public void setMark(Integer Mark) { this.Mark = Mark; }
    /**
     *
     * @param Status
     * The status
     */
    public void setStatus(String Status) {
        this.Status = Status;
    }
    /**
     *
     * @param Category
     * The category
     */
    public void setCategory(String Category) {
        this.Category = Category;
    }
    /**
     *
     * @param Content
     * The content
     */
    public void setContent(String Content) { this.Content = Content; }
    /**
     *
     * @param Comments
     * The comments
     */
    public void setComments(String Comments) {
        this.Comments = Comments;
    }
    /**
     *
     * @return
     * The Id
     */
    public Integer getId() { return Id; }
    /**
     *
     * @return
     * The Name
     */
    public String getName() { return Name; }
    /**
     *
     * @return
     * The Owner
     */
    public UserOwner getOwner() { return Owner; }
    public String getOwnerStr() { return (Owner.getUserName()); }
    /**
     *
     * @return
     * The CoverUri
     */
    public String getCoverUri() { return CoverUri; }
    /**
     *
     * @return
     * The Votes
     */
    public Integer getVotes() { return Votes; }
    public String getVotesStr() { return (" " + Votes + "%"); }
    /**
     *
     * @return
     * The Mark
     */
    public Integer getMark() { return Mark; }
    /**
     *
     * @return
     * The Status
     */
    public String getStatus() {
        return Status;
    }
    /**
     *
     * @return
     * The Category
     */
    public String getCategory() {
        return Category;
    }
    /**
     *
     * @return
     * The Content
     */
    public String getContent() {
        return Content;
    }
    /**
     *
     * @return
     * The Comments
     */
    public String getComments() {
        return Comments;
    }
}
