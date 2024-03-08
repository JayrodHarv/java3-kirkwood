package edu.kirkwood.smp.models;

import edu.kirkwood.shared.Validators;

import java.time.Instant;
import java.util.regex.Matcher;

public class User {
    private int UserID;
    private String Email;
    private char[] Password;
    private String DisplayName;
    private String Language;
    private String Status;
    private String Privileges;
    private Instant CreatedAt;
    private Instant LastLoggedIn;
    private Instant UpdatedAt;
    private byte[] Pfp;

    public User() {

    }

    public User(int userID, String email, char[] password, String displayName, String language, String status, String privileges, Instant createdAt, Instant lastLoggedIn, Instant updatedAt, byte[] pfp) {
        UserID = userID;
        Email = email;
        Password = password;
        DisplayName = displayName;
        Language = language;
        Status = status;
        Privileges = privileges;
        CreatedAt = createdAt;
        LastLoggedIn = lastLoggedIn;
        UpdatedAt = updatedAt;
        Pfp = pfp;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Matcher matcher = Validators.emailPattern.matcher(email);
        if(!matcher.matches()) {
            throw new IllegalArgumentException("Invalid Email Address");
        }
        this.Email = email;
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

    public String getPrivileges() {
        return Privileges;
    }

    public void setPrivileges(String privileges) {
        Privileges = privileges;
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
}
