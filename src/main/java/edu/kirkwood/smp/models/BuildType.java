package edu.kirkwood.smp.models;

public class BuildType {
    private String BuildTypeID;
    private String Description;

    public BuildType() {
    }

    public BuildType(String buildTypeID, String description) {
        BuildTypeID = buildTypeID;
        Description = description;
    }

    public String getBuildTypeID() {
        return BuildTypeID;
    }

    public void setBuildTypeID(String buildTypeID) {
        BuildTypeID = buildTypeID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
