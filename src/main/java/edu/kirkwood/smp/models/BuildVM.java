package edu.kirkwood.smp.models;

import java.time.Instant;
import java.util.Date;

public class BuildVM {
    private String BuildID;
    private byte[] Image;
    private Date DateBuilt;
    private Instant CreatedAt;
    private String Description;
    private User user;
    private World World;
    private BuildType BuildType;

    public BuildVM(String buildID, byte[] image, Date dateBuilt, Instant createdAt, String description, User user, edu.kirkwood.smp.models.World world, edu.kirkwood.smp.models.BuildType buildType) {
        BuildID = buildID;
        Image = image;
        DateBuilt = dateBuilt;
        CreatedAt = createdAt;
        Description = description;
        this.user = user;
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
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public edu.kirkwood.smp.models.World getWorld() {
        return World;
    }

    public void setWorld(edu.kirkwood.smp.models.World world) {
        World = world;
    }

    public edu.kirkwood.smp.models.BuildType getBuildType() {
        return BuildType;
    }

    public void setBuildType(edu.kirkwood.smp.models.BuildType buildType) {
        BuildType = buildType;
    }
}
