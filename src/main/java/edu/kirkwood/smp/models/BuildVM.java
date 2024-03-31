package edu.kirkwood.smp.models;

import java.time.Instant;
import java.util.Date;

public class BuildVM {
    private String BuildID;
    private byte[] Image;
    private Date DateBuilt;
    private String Coordinates;
    private Instant CreatedAt;
    private String Description;
    private User User;
    private World World;
    private BuildType BuildType;

    public BuildVM(String buildID, byte[] image, Date dateBuilt, String coordinates, Instant createdAt, String description, User user, World world, BuildType buildType) {
        BuildID = buildID;
        Image = image;
        DateBuilt = dateBuilt;
        Coordinates = coordinates;
        CreatedAt = createdAt;
        Description = description;
        this.User = user;
        World = world;
        BuildType = buildType;
    }

    public String getBuildID() {
        return BuildID;
    }

    public void setBuildID(String buildID) {
        BuildID = buildID;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public Date getDateBuilt() {
        return DateBuilt;
    }

    public void setDateBuilt(Date dateBuilt) {
        DateBuilt = dateBuilt;
    }

    public String getCoordinates() {
        return Coordinates;
    }

    public void setCoordinates(String coordinates) {
        Coordinates = coordinates;
    }

    public Instant getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Instant createdAt) {
        CreatedAt = createdAt;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        User = user;
    }

    public World getWorld() {
        return World;
    }

    public void setWorld(World world) {
        World = world;
    }

    public BuildType getBuildType() {
        return BuildType;
    }

    public void setBuildType(BuildType buildType) {
        BuildType = buildType;
    }
}
