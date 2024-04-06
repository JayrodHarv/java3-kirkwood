package edu.kirkwood.smp.models;

import java.util.List;

public class VoteVM extends Vote {
    private int NumberOfOptions;
    private String UserDisplayName;
    private List<VoteOption> Options;

    public int getNumberOfOptions() {
        return NumberOfOptions;
    }

    public void setNumberOfOptions(int numberOfOptions) {
        NumberOfOptions = numberOfOptions;
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
}
