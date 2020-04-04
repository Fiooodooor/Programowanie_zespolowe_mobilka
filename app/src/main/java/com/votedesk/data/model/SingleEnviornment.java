package com.votedesk.data.model;

import java.util.List;

public class SingleEnviornment {
    private Integer Id;
    private String Name;
    private String Owner;
    private String CoverUri;
    private List<SingleProject> ProjectList;

    SingleEnviornment(Integer Id, String Name, String Owner, String CoverUri, List<SingleProject> ProjectList) {
        this.Id = Id;
        this.Name = Name;
        this.Owner = Owner;
        this.CoverUri = CoverUri;
        this.ProjectList = ProjectList;
    }

}
