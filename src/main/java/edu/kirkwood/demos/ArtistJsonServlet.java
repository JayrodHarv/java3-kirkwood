package edu.kirkwood.demos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

@WebServlet("/artist")
public class ArtistJsonServlet extends HttpServlet {
    private static List<Artist> artists;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("artist", artists.get(0));
        req.getRequestDispatcher("WEB-INF/demos/artist.jsp").forward(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        JSONObject json = null;
        try {
            json = JsonReader.readJsonFromUrl("https://api.deezer.com/search/artist?q=imagine%20dragons");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ArtistFromJson artistFromJson = null;
        try {
            artistFromJson = mapper.readValue(json.toString(), ArtistFromJson.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        artists = artistFromJson.getArtists();
    }
}
