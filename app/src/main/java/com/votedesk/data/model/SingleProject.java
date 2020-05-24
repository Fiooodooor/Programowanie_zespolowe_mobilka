package com.votedesk.data.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private boolean Vote_opened;
    private String vote_starting;
    private Date vote_starting_date;
    private String vote_closing;
    private Date vote_closing_date;

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
        setCan_vote(sProject.optBoolean("can_vote", false));
        setVote_opened(sProject.optBoolean("vote_opened", false));
        setVote_starting(sProject.optString("vote_starting", "2020-05-23T00:00:01.000000"));
        setVote_closing(sProject.optString("vote_closing", "2099-05-23T00:00:01.000000"));
        setVotes(sProject.optInt("vote_average", 0));

        setEnvironment(sProject.optInt("environment", 0));
        setEnvironment_name(sProject.optString("environment_name", "no name"));
        setStatus(sProject.optString("project_status", "DataError"));
        setCategory(sProject.optString("project_category", "DataError"));
        setComments("some comments");
    }

    public boolean isVote_opened() {
        return Vote_opened;
    }

    private void setVote_opened(boolean vote_opened) {
        Vote_opened = vote_opened;
    }

    public String getVote_starting() {
        SimpleDateFormat formatter =  new SimpleDateFormat("dd MMMM hh:mm", Locale.getDefault());
        return formatter.format(this.vote_starting);
    }

    private void setVote_starting(String vote_starting) {
        this.vote_starting = vote_starting;
        try {
            this.vote_starting_date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault()).parse(vote_starting);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getVote_closing() {
        SimpleDateFormat formatter =  new SimpleDateFormat("dd MMMM hh:mm", Locale.getDefault());
        return formatter.format(this.vote_closing_date);
    }

    private void setVote_closing(String vote_closing) {
        this.vote_closing = vote_closing;
        try {
            this.vote_closing_date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault()).parse(vote_closing);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isCan_vote() {
        return Can_vote;
    }

    private void setCan_vote(boolean can_vote) {
        Can_vote = can_vote;
    }

    public Integer getEnvironment() {
        return Environment;
    }

    private void setEnvironment(Integer environment) {
        Environment = environment;
    }

    public String getEnvironment_name() {
        return Environment_name;
    }

    private void setEnvironment_name(String environment_name) {
        Environment_name = environment_name;
    }

    /**
     *
     * @param Id
     * The global project unique id
     */
    public void setId(Integer Id) { this.Id = Id;    }
    /**
     *
     * @param Name
     * The project name
     */
    public void setName(String Name) { this.Name = Name;    }
    /**
     *
     * @param Owner
     * The project owner user struct
     */
    private void setOwner(UserOwner Owner) { this.Owner = Owner;    }
    /**
     *
     * @param coverUri
     * The cover_uri relative to site address.
     */
    private void setCoverUri(String coverUri) {
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
    private void setVotes(Integer Votes) { this.Votes = Votes;    }
    /**
     *
     * @param Mark
     * The mark
     */
    private void setMark(Integer Mark) { this.Mark = Mark; }
    /**
     *
     * @param Status
     * The status
     */
    private void setStatus(String Status) {
        this.Status = Status;
    }
    /**
     *
     * @param Category
     * The category
     */
    private void setCategory(String Category) {
        this.Category = Category;
    }
    /**
     *
     * @param Content
     * The content
     */
    private void setContent(String Content) { this.Content = Content; }
    /**
     *
     * @param Comments
     * The comments
     */
    private void setComments(String Comments) {
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
    public String getOwnerStr() { return (getOwner().getUserName()); }
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
    private Integer getVotes() { return Votes; }
    public String getVotesStr() { return (" " + getVotes() + "%"); }
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
