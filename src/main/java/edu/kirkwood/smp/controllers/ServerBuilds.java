package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.TagDAO;
import edu.kirkwood.smp.models.Tag;
import edu.kirkwood.smp.data.BuildDAO;
import edu.kirkwood.smp.models.Build;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

@WebServlet("/server-builds")
public class ServerBuilds extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> results = new HashMap<>();
        List<Build> builds = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();
        try {
            builds = BuildDAO.getAll();
            for (Build b : builds) {
                // TODO: Maybe make each image have it's own url
                if(b.getImage() == null) continue;
                req.setAttribute(b.getBuildID() + "", "data:image/png;base64," + new String(Base64.getDecoder().decode(b.getImage())));
            }
            tags = TagDAO.getAll();
        } catch(Exception e) {
            results.put("getBuildListError", "Failed to retrieve the list of buildings.");
        }

        req.setAttribute("results", results);
        req.setAttribute("buildings", builds);
        req.setAttribute("tags", tags);
        req.setAttribute("pageTitle", "Server Builds");
        req.getRequestDispatcher("WEB-INF/smp/server-builds.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
