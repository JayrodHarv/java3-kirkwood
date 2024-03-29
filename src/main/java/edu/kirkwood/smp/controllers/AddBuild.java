package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.BuildDAO;
import edu.kirkwood.smp.data.BuildTypeDAO;
import edu.kirkwood.smp.data.WorldDAO;
import edu.kirkwood.smp.models.Build;
import edu.kirkwood.smp.models.BuildType;
import edu.kirkwood.smp.models.User;
import edu.kirkwood.smp.models.World;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;

@WebServlet("/add-build")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,    // 1 MB
        maxFileSize = 1024 * 1024 * 10,     // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
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

        try {
            List<World> worlds = WorldDAO.getAll();
            req.setAttribute("worlds", worlds);
        } catch(Exception e) {
            results.put("worldError", "Failed to retrieve worlds. Please refresh the page.");
        }

        try {
            List<BuildType> buildTypes = BuildTypeDAO.getAll();
            req.setAttribute("buildTypes", buildTypes);
        } catch(Exception e) {
            results.put("buildTypeError", "Failed to retrieve build types. Please refresh the page.");
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Add Build");
        req.getRequestDispatcher("WEB-INF/smp/add-build.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userFromSession = (User)session.getAttribute("activeSMPUser");

        Map<String, String> results = new HashMap<>();

        String buildName = req.getParameter("buildName");

        // TODO: possibly add a way to add contributors and get the player head image using the Minecraft API
        String description = req.getParameter("description");
        // TODO: Image stuff that I don't know how to do yet
        try {
            Part imgPart = req.getPart("image");
            String imgName = imgPart.getSubmittedFileName();

            for(Part part : req.getParts()) {
                part.write("C:\\upload\\" + imgName);
            }
        } catch (Exception e) {
//            results.put("imageError", "Something went wrong when trying to upload this image.");
            results.put("imageError", e.getMessage());
        }

        // Tags
        String world = req.getParameter("world");
        String buildType = req.getParameter("buildType");

        results.put("buildName", buildName);
        results.put("description", description);
        results.put("world", world);
        results.put("buildType", buildType);

        // Input Validation
        if(buildName == null || buildName.isEmpty()) {
            results.put("buildNameError", "This new build must have a name.");
        }
        if(description == null || description.isEmpty()) {
            results.put("descriptionError", "Description is required. Ideas of what to include in description: build time, built by who, inspired by...");
        }
//        if(world == null || world.isEmpty()) {
//            results.put("worldError", "You must add at least one tag to this new build.");
//        }

//        if (!results.containsKey("buildNameError") && !results.containsKey("descriptionError")
//                && !results.containsKey("tagError")
//        ) {
//            try {
//                Build build = new Build(buildName, userFromSession.getUserID(), image, Instant.now().atOffset(ZoneOffset.UTC).toInstant(), description);
//                if(BuildDAO.add(build)) {
//                    session.setAttribute("flashMessageSuccess", "Building Successfully Added!");
//                    resp.sendRedirect("server-builds");
//                    return;
//                }
//            } catch(RuntimeException e) {
//                results.put("buildAddFail", "Failed to add building");
//            }
//        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Add Build");
        req.getRequestDispatcher("WEB-INF/smp/add-build.jsp").forward(req, resp);
    }
}
