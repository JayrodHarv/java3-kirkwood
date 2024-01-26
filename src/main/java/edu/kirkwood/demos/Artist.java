package edu.kirkwood.demos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Artist {
    @JsonProperty("name")
    private String name;

    @JsonProperty("picture")
    private String picture;

    public Artist() {
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
