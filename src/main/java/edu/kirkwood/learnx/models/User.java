package edu.kirkwood.learnx.models;

import edu.kirkwood.shared.Validators;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.regex.Matcher;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private char[] password;
    private String language;
    private String status;
    private String privileges;
    private Instant created_at;
    private Instant last_logged_in;
    private Instant updated_at;
    private static LocalDate birthday = LocalDate.of(1999, 2, 14);

    public User() {

    }

    public User(int id, String firstName, String lastName, String email, String phone, char[] password, String language, String status, String privileges, Instant created_at, Instant last_logged_in, Instant updated_at) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.language = language;
        this.status = status;
        this.privileges = privileges;
        this.created_at = created_at;
        this.last_logged_in = last_logged_in;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        Matcher matcher = Validators.emailPattern.matcher(email);
        if(!matcher.matches()) {
            throw new IllegalArgumentException("Invalid email address");
        }
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        // todo: validate phone
        this.phone = phone;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        // The only time the password should be null is when I set the activeUser session attribute
        if(password == null) {
            this.password = password;
            return;
        }
        String passwordStr = String.valueOf(password);
        Matcher matcher = Validators.passwordPattern.matcher(passwordStr);
        if(!matcher.matches()) {
            throw new IllegalArgumentException("Password must be 8 characters, with 3 of 4 (lowercase, uppercase, number, symbol)");
        }
        this.password = password;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        Matcher matcher = Validators.languagePattern.matcher(language);
        if(!matcher.matches()) {
            throw new IllegalArgumentException("Invalid Language");
        }
        this.language = language;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        // todo: validate status
        this.status = status;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        // todo: validate privileges
        this.privileges = privileges;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public Date getCreated_Date() {
        return Date.from(created_at);
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public Instant getLast_logged_in() {
        return last_logged_in;
    }

    public Date getLast_logged_Date() {
        return Date.from(last_logged_in);
    }

    public void setLast_logged_in(Instant last_logged_in) {
        this.last_logged_in = last_logged_in;
    }

    public Instant getUpdated_at() {
        return updated_at;
    }

    public Date getUpdated_Date() {
        return Date.from(updated_at);
    }

    public void setUpdated_at(Instant updated_at) {
        this.updated_at = updated_at;
    }

    public static LocalDate getBirthday() {
        return birthday;
    }

    public Date getBirthday_Date() {
        return java.sql.Date.valueOf(birthday);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
