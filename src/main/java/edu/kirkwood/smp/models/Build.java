package edu.kirkwood.smp.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Build {
    private String BuildID;
    private String UserID;
    private byte[] Image;
    private String WorldID;
    private String BuildTypeID;
    private Date DateBuilt;
    private String Coordinates;
    private Instant CreatedAt;
    private String Description;
    private String Base64Image;

    public Build() {

    }

    public Build(String buildID, String userID, byte[] image, String worldID, String buildTypeID, Date dateBuilt, String coordinates, Instant createdAt, String description) {
        BuildID = buildID;
        UserID = userID;
        Image = image;
        WorldID = worldID;
        BuildTypeID = buildTypeID;
        DateBuilt = dateBuilt;
        Coordinates = coordinates;
        CreatedAt = createdAt;
        Description = description;
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

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public String getWorldID() {
        return WorldID;
    }

    public void setWorldID(String worldID) {
        WorldID = worldID;
    }

    public String getBuildTypeID() {
        return BuildTypeID;
    }

    public void setBuildTypeID(String buildTypeID) {
        BuildTypeID = buildTypeID;
    }

    public Date getDateBuilt() {
        return DateBuilt;
    }

    public void setDateBuilt(Date dateBuilt) {
        DateBuilt = dateBuilt;
    }
    public String getDateBuiltString() {
        if(DateBuilt == null) return "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(DateBuilt);
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

    public String getBase64Image() {
        return Base64Image;
    }

    public void setBase64Image(String base64Image) {
        Base64Image = base64Image;
    }
}
