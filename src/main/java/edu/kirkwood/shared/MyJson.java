package edu.kirkwood.shared;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import java.io.StringWriter;

public class MyJson {
    private JsonObject json;

    public MyJson(JsonObject json) {
        this.json = json;
    }

    public JsonObject getJson() {
        return json;
    }

    public void setJson(JsonObject json) {
        this.json = json;
    }

    public String toString() {
        StringWriter writer = new StringWriter();
        Json.createWriter(writer).write(this.json);
        return writer.toString();
    }
}
