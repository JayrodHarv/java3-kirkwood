package edu.kirkwood.smp.models;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalField;

public class Vote {
    private String VoteID;
    private String UserID;
    private String Description;
    private Instant StartTime;
    private Instant EndTime;

    public Vote() {
    }

    public Vote(String voteID, String userID, String description) {
        VoteID = voteID;
        UserID = userID;
        Description = description;
    }

    public Vote(String voteID, String userID, String description, Instant startTime, Instant endTime) {
        VoteID = voteID;
        UserID = userID;
        Description = description;
        StartTime = startTime;
        EndTime = endTime;
    }

    public String getVoteID() {
        return VoteID;
    }

    public void setVoteID(String voteID) {
        VoteID = voteID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Instant getStartTime() {
        return StartTime;
    }

    public void setStartTime(Instant startTime) {
        StartTime = startTime;
    }

    public Instant getEndTime() {
        return EndTime;
    }

    public void setEndTime(Instant endTime) {
        EndTime = endTime;
    }

    public String getEndTimeString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a").withZone(ZoneId.systemDefault());
        return formatter.format(EndTime);
    }

    public String getStartTimeString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a").withZone(ZoneId.systemDefault());
        return formatter.format(StartTime);
    }

}
