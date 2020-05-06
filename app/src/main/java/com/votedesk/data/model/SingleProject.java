package com.votedesk.data.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SingleProject implements Serializable {
    private Integer Id;
    private String  Name;
    private Integer Owner;
    private String  CoverUri;
    private String  Status;
    private String  Category;
    private String  Content;
    private String Comments;
    private Integer Votes;
    private Integer Mark;

    public SingleProject(JSONObject sProject)  throws JSONException {
        this(sProject, false);
    }
    public SingleProject(JSONObject sProject, boolean sExtend)  throws JSONException {
        setId(sProject.getInt("id"));
        setOwner(sProject.getInt("owner"));
        setName(sProject.getString("project_name"));
        setCoverUri(sProject.optString("cover", "@mipmap/ic_launcher"));
        setVotes(0);
        setMark(0);
        if(sExtend) {
            setStatus(sProject.getString("project_status"));
            setCategory(sProject.getString("project_category"));
            setContent(sProject.getString("project_content"));
            setComments("some comments");
        }
    }

    SingleProject(SingleProject cp) {
        this.Id       = cp.Id;
        this.Name     = cp.Name;
        this.Owner    = cp.Owner;
        this.CoverUri = cp.CoverUri;
        this.Votes    = cp.Votes;
        this.Mark     = cp.Mark;
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
    public void setOwner(Integer Owner) { this.Owner = Owner;    }
    /**
     *
     * @param CoverUri
     * The cover_uri
     */
    public void setCoverUri(String CoverUri) { this.CoverUri = CoverUri;    }
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
    public Integer getOwner() { return Owner; }
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
