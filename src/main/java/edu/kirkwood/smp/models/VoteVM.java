package edu.kirkwood.smp.models;

import java.util.List;

public class VoteVM extends Vote {
    private int NumberOfOptions;
    private int NumberOfVotes;
    private String UserDisplayName;
    private List<VoteOption> Options;
    private List<UserVote> UserVotes;

    public int getNumberOfOptions() {
        return NumberOfOptions;
    }

    public void setNumberOfOptions(int numberOfOptions) {
        NumberOfOptions = numberOfOptions;
    }

    public int getNumberOfVotes() {
        return NumberOfVotes;
    }

    public void setNumberOfVotes(int numberOfVotes) {
        NumberOfVotes = numberOfVotes;
    }

    public String getUserDisplayName() {
        return UserDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        UserDisplayName = userDisplayName;
    }

    public List<VoteOption> getOptions() {
        return Options;
    }

    public void setOptions(List<VoteOption> options) {
        Options = options;
    }

    public List<UserVote> getUserVotes() {
        return UserVotes;
    }

    public void setUserVotes(List<UserVote> userVotes) {
        UserVotes = userVotes;
    }

    public int getPercentVotes(int numVotes) {
        if (numVotes <= 0) return 0;
        return Math.round((numVotes / (float)NumberOfVotes) * 100);
    }
}
