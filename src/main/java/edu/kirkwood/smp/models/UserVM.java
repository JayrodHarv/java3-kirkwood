package edu.kirkwood.smp.models;

import java.time.Instant;
import java.util.Date;

public class UserVM {
    private String UserID;
    private String DisplayName;
    private String Language;
    private String Status;
    private Role Role;
    private Instant CreatedAt;
    private Instant LastLoggedIn;
    private Instant UpdatedAt;
    private byte[] Pfp;
    private String Base64Pfp;

    public UserVM() {
    }

    public UserVM(String userID, String displayName, String language, String status, edu.kirkwood.smp.models.Role role, Instant createdAt, Instant lastLoggedIn, Instant updatedAt, byte[] pfp) {
        UserID = userID;
        DisplayName = displayName;
        Language = language;
        Status = status;
        Role = role;
        CreatedAt = createdAt;
        LastLoggedIn = lastLoggedIn;
        UpdatedAt = updatedAt;
        Pfp = pfp;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public edu.kirkwood.smp.models.Role getRole() {
        return Role;
    }

    public void setRole(edu.kirkwood.smp.models.Role role) {
        Role = role;
    }

    public Instant getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Instant createdAt) {
        CreatedAt = createdAt;
    }

    public Instant getLastLoggedIn() {
        return LastLoggedIn;
    }

    public void setLastLoggedIn(Instant lastLoggedIn) {
        LastLoggedIn = lastLoggedIn;
    }

    public Instant getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        UpdatedAt = updatedAt;
    }

    public byte[] getPfp() {
        return Pfp;
    }

    public void setPfp(byte[] pfp) {
        Pfp = pfp;
    }

    public String getBase64Pfp() {
        return Base64Pfp;
    }

    public void setBase64Pfp(String base64Pfp) {
        Base64Pfp = base64Pfp;
    }

    public Date getCreatedAtDate() {
        return Date.from(CreatedAt);
    }

    public Date getLastLoggedInDate() {
        return Date.from(LastLoggedIn);
    }

    public Date getUpdatedAtDate() {
        return Date.from(UpdatedAt);
    }
}
