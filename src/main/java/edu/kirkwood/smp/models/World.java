package edu.kirkwood.smp.models;

import java.util.Date;

public class World {
    private String WorldID;
    private Date DateStarted;
    private String Description;

    public World() {
    }

    public World(String worldID, Date dateStarted, String description) {
        WorldID = worldID;
        DateStarted = dateStarted;
        Description = description;
    }

    public String getWorldID() {
        return WorldID;
    }

    public void setWorldID(String worldID) {
        WorldID = worldID;
    }

    public Date getDateStarted() {
        return DateStarted;
    }

    public void setDateStarted(Date dateStarted) {
        DateStarted = dateStarted;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

}
