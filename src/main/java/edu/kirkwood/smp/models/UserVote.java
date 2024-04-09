package edu.kirkwood.smp.models;

import java.time.Instant;

public class UserVote {
    private String UserID;
    private String VoteID;
    private int OptionID;
    private Instant VoteTime;

    public UserVote() {
    }

    public UserVote(String userID, String voteID, int optionID, Instant voteTime) {
        UserID = userID;
        VoteID = voteID;
        OptionID = optionID;
        VoteTime = voteTime;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getVoteID() {
        return VoteID;
    }

    public void setVoteID(String voteID) {
        VoteID = voteID;
    }

    public int getOptionID() {
        return OptionID;
    }

    public void setOptionID(int optionID) {
        OptionID = optionID;
    }

    public Instant getVoteTime() {
        return VoteTime;
    }

    public void setVoteTime(Instant voteTime) {
        VoteTime = voteTime;
    }
}
