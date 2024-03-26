package edu.kirkwood.smp.models;

public class Tag {
    private String TagID;
    private String Description;

    public Tag(String tagID, String description) {
        TagID = tagID;
        Description = description;
    }

    public String getTagID() {
        return TagID;
    }

    public void setTagID(String tagID) {
        TagID = tagID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
