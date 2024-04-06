package edu.kirkwood.smp.models;

public class VoteListItemVM extends Vote {
    private int NumberOfVotes;
    private String UserDisplayName;

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
}
