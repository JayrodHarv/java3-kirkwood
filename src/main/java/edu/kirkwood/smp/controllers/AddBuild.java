package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.BuildDAO;
import edu.kirkwood.smp.data.TagDAO;
import edu.kirkwood.smp.models.Build;
import edu.kirkwood.smp.models.Tag;
import edu.kirkwood.smp.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;

@WebServlet("/add-build")
public class AddBuild extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userFromSession = (User)session.getAttribute("activeSMPUser");
        if(userFromSession == null || !userFromSession.getStatus().equals("active")) {
            session.setAttribute("flashMessageWarning", "You must be logged in to add a new building.");
            resp.sendRedirect("smp-login");
            return;
        }
        Map<String, String> results = new HashMap<>();
        List<Tag> tags = new ArrayList<>();
        try {
            tags = TagDAO.getAll();
        } catch(Exception e) {
            results.put("getTagsError", "Failed to retrieve the building tags.");
        }

        req.setAttribute("results", results);
        req.setAttribute("tags", tags);
        req.setAttribute("pageTitle", "Add Build");
        req.getRequestDispatcher("WEB-INF/smp/add-build.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userFromSession = (User)session.getAttribute("activeSMPUser");

        String buildName = req.getParameter("buildName");

        // TODO: possibly add a way to add contributors and get the player head image using the Minecraft API
        String description = req.getParameter("description");
        // TODO: Image stuff that I don't know how to do yet

        String[] inputTags = req.getParameterValues("tags");

        Map<String, String> results = new HashMap<>();
        results.put("buildName", buildName);
        results.put("description", description);

        // Input Validation
        if(buildName == null || buildName.isEmpty()) {
            results.put("buildNameError", "This new build must have a name.");
        }
        if(description == null || description.isEmpty()) {
            results.put("descriptionError", "Description is required. Ideas of what to include in description: build time, built by who, inspired by...");
        }
        if(inputTags == null || inputTags.length == 0) {
            results.put("tagError", "You must add at least one tag to this new build.");
        } else {
            List<String> tagStrList = Arrays.asList(inputTags);
            req.setAttribute("selectedTags", tagStrList);
        }

        if (!results.containsKey("buildNameError") && !results.containsKey("descriptionError")
                && !results.containsKey("tagError")
        ) {
            try {
                List<Tag> tags = new ArrayList<>();
                for(String tagID : inputTags) {
                    tags.add(new Tag(tagID, "beans"));
                }
                Build build = new Build(buildName, userFromSession.getUserID(), tags, Instant.now().atOffset(ZoneOffset.UTC).toInstant(), description);
                if(BuildDAO.add(build)) {
                    session.setAttribute("flashMessageSuccess", "Building Successfully Added!");
                    resp.sendRedirect("server-builds");
                    return;
                }
            } catch(RuntimeException e) {
                results.put("buildAddFail", "Failed to add building");
            }
        }

        List<Tag> tags = new ArrayList<>();
        try {
            tags = TagDAO.getAll();
        } catch(Exception e) {
            results.put("getTagsError", "Failed to retrieve the building tags.");
        }

        req.setAttribute("results", results);
        req.setAttribute("tags", tags);
        req.setAttribute("pageTitle", "Add Build");
        req.getRequestDispatcher("WEB-INF/smp/add-build.jsp").forward(req, resp);
    }
}
