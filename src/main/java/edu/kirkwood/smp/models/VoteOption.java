package edu.kirkwood.smp.models;

public class VoteOption {
    private int OptionID;
    private String VoteID;
    private String Title;
    private String Description;
    private byte[] Image;
    private String Base64Image;

    public VoteOption() {
    }

    public VoteOption(String voteID, String title, String description, byte[] image) {
        VoteID = voteID;
        Title = title;
        Description = description;
        Image = image;
    }

    public int getOptionID() {
        return OptionID;
    }

    public void setOptionID(int optionID) {
        OptionID = optionID;
    }

    public String getVoteID() {
        return VoteID;
    }

    public void setVoteID(String voteID) {
        VoteID = voteID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public String getBase64Image() {
        return Base64Image;
    }

    public void setBase64Image(String base64Image) {
        Base64Image = base64Image;
    }
}
