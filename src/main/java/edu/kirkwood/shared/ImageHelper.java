package edu.kirkwood.shared;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class ImageHelper {
    public static byte[] getImageBytesFromInputStream(InputStream inputStream) throws IOException {
        // Source: https://www.codejava.net/coding/how-to-display-images-from-database-in-jsp-page-with-java-servlet
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        byte[] Image = outputStream.toByteArray();
        outputStream.close();
        inputStream.close();
        return Image;
    }

    public static String getBase64Image(String imageType, byte[] image) {
        return "data:" + imageType + ";base64," + Base64.getEncoder().encodeToString(image);
    }
}
