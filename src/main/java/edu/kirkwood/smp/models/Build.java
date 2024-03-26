package edu.kirkwood.smp.models;

import java.time.Instant;
import java.util.List;

public class Build {
    private String BuildID;
    private String UserID;
    private List<Tag> Tags;
    private byte[] Image;
    private Instant CreatedAt;
    private String Description;

    public Build() {

    }

    public Build(String buildID, String userID, List<Tag> tags, Instant createdAt, String description) {
        BuildID = buildID;
        UserID = userID;
        Tags = tags;
        CreatedAt = createdAt;
        Description = description;
    }

    public Build(String buildID, String userID, List<Tag> tags, byte[] image, Instant createdAt, String description) {
        BuildID = buildID;
        UserID = userID;
        Tags = tags;
        Image = image;
        CreatedAt = createdAt;
        Description = description;
    }

    public Instant getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Instant createdAt) {
        CreatedAt = createdAt;
    }

    public String getBuildID() {
        return BuildID;
    }

    public void setBuildID(String buildID) {
        BuildID = buildID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public List<Tag> getTags() {
        return Tags;
    }

    public void setTags(List<Tag> tags) {
        Tags = tags;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
