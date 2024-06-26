package edu.kirkwood.smp.models;

import edu.kirkwood.shared.Validators;

import java.time.Instant;
import java.util.Date;
import java.util.regex.Matcher;

public class User {
    private String UserID;
    private char[] Password;
    private String DisplayName;
    private String Language;
    private String Status;
    private String RoleID;
    private Instant CreatedAt;
    private Instant LastLoggedIn;
    private Instant UpdatedAt;
    private byte[] Pfp;
    private String Base64Pfp;

    public User() {

    }

    public User(String userID, String displayName, byte[] pfp) {
        UserID = userID;
        DisplayName = displayName;
        Pfp = pfp;
    }

    public User(String userID, char[] password, String displayName, String language, String status, String role, Instant createdAt, Instant lastLoggedIn, Instant updatedAt, byte[] pfp) {
        UserID = userID;
        Password = password;
        DisplayName = displayName;
        Language = language;
        Status = status;
        RoleID = role;
        CreatedAt = createdAt;
        LastLoggedIn = lastLoggedIn;
        UpdatedAt = updatedAt;
        Pfp = pfp;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        Matcher matcher = Validators.emailPattern.matcher(userID);
        if(!matcher.matches()) {
            throw new IllegalArgumentException("Invalid Email Address");
        }
        this.UserID = userID;
    }

    public char[] getPassword() {
        return Password;
    }

    public void setPassword(char[] password) {
        // The only time the password should be null is when I set the activeUser session attribute
        if(password == null) {
            this.Password = password;
            return;
        }
        String pass = password.toString();
        Matcher matcher = Validators.passwordPattern.matcher(pass);
        if(!matcher.matches()) {
            throw new IllegalArgumentException("Password must be 8 characters, with 3 of 4 (lowercase, uppercase, number, symbol)");
        }
        this.Password = password;
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

    public String getRole() {
        return RoleID;
    }

    public void setRole(String role) {
        RoleID = role;
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
    public byte[] getPfp() { return Pfp; }

    public void setPfp(byte[] pfp) { Pfp = pfp; }

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
