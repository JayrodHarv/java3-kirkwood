package edu.kirkwood.smp.models;

import java.time.Instant;
import java.util.List;

public class Building {
    private int BuildingID;
    private int UserID;
    private List<Tag> Tags;
    private String Name;
    private byte[] Image;
    private Instant CreatedAt;
    private String Description;

    public Building(int userID, List<Tag> tags, String name, Instant createdAt, String description) {
        UserID = userID;
        Tags = tags;
        Name = name;
        CreatedAt = createdAt;
        Description = description;
    }

    public Building(int buildingID, int userID, List<Tag> tags, String name, byte[] image, Instant createdAt, String description) {
        BuildingID = buildingID;
        UserID = userID;
        Tags = tags;
        Name = name;
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

    public int getBuildingID() {
        return BuildingID;
    }

    public void setBuildingID(int buildingID) {
        BuildingID = buildingID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public List<Tag> getTags() {
        return Tags;
    }

    public void setTags(List<Tag> tags) {
        Tags = tags;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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
