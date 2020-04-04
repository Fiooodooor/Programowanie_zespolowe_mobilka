package com.votedesk.data.model;

import java.util.List;

public class SingleProject {
    private Integer Id;
    private String  Name;
    private String  Owner;
    private String  CoverUri;
    private Integer Votes;
    private Integer Mark;

    SingleProject() {
    }
    SingleProject(Integer Id, String Name, String Owner, String CoverUri, Integer Votes, Integer Mark) {
        this.Id       = Id;
        this.Name     = Name;
        this.Owner    = Owner;
        this.CoverUri = CoverUri;
        this.Votes    = Votes;
        this.Mark     = Mark;
    }
    SingleProject(SingleProject cp) {
        this.Id       = cp.Id;
        this.Name     = cp.Name;
        this.Owner    = cp.Owner;
        this.CoverUri = cp.CoverUri;
        this.Votes    = cp.Votes;
        this.Mark     = cp.Mark;
    }
}
