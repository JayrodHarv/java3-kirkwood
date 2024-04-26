package edu.kirkwood.shared;

import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;

import java.io.StringReader;

public class MyDecoder implements Decoder.Text<MyJson> {
    @Override
    public MyJson decode(String s) throws DecodeException {
        JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
        return new MyJson(jsonObject);
    }

    @Override
    public boolean willDecode(String s) {
        boolean result;
        try {
            JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();
            result = true;
        } catch (JsonException jex) {
            result = false;
        }
        return result;
    }
}
