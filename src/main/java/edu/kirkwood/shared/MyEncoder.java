package edu.kirkwood.shared;

import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;

public class MyEncoder implements Encoder.Text<MyJson> {
    @Override
    public String encode(MyJson myJson) throws EncodeException {
        return myJson.toString();
    }
}
