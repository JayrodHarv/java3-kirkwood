package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.BuildDAO;
import edu.kirkwood.smp.data.BuildTypeDAO;
import edu.kirkwood.smp.data.WorldDAO;
import edu.kirkwood.smp.models.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;

@WebServlet("/edit-build")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,    // 1 MB
        maxFileSize = 1024 * 1024 * 10,     // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class EditBuild extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserVM userFromSession = (UserVM)session.getAttribute("activeSMPUser");
        if(userFromSession == null || !userFromSession.getStatus().equals("active")) {
            session.setAttribute("flashMessageWarning", "You must be logged in to add a new building.");
            resp.sendRedirect("smp-login?redirect=edit-build");
            return;
        }

        String buildName = req.getParameter("build_id");

        Map<String, String> results = new HashMap<>();

        try {
            Build build = BuildDAO.get(buildName);
            results.put("buildName", build.getBuildID());
            results.put("description", build.getDescription());
            results.put("dateBuilt", build.getDateBuiltString());

            results.put("base64Image", build.getBase64Image());

            results.put("world", build.getWorldID());
            results.put("buildType", build.getBuildTypeID());

            String coordinates = build.getCoordinates();
            if(coordinates != null && !coordinates.isEmpty()) {
                String[] coords = coordinates.split(", ");
                results.put("xCoord", coords[0]);
                results.put("yCoord", coords[1]);
                results.put("zCoord", coords[2]);
            }
        } catch(Exception e) {
            results.put("buildEditFail", "Something went wrong. Please try again." + e.getMessage());
        }

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
        req.setAttribute("pageTitle", "Edit Build");
        req.getRequestDispatcher("WEB-INF/smp/edit-build.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserVM userFromSession = (UserVM)session.getAttribute("activeSMPUser");

        Map<String, String> results = new HashMap<>();

        String buildName = req.getParameter("buildName");

        // TODO: possibly add a way to add contributors and get the player head image using the Minecraft API
        String description = req.getParameter("description");

        // Date Built
        String dateBuilt = req.getParameter("dateBuilt");
        Date dateBuiltDate = null;
        if(dateBuilt != null && !dateBuilt.isEmpty()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
                dateBuiltDate = formatter.parse(dateBuilt);
            } catch (ParseException e) {
                results.put("dateBuildError", "Date entered was incorrect or in the wrong format.");
            }
        }

        // Using image retrieval found in this video
        // https://www.youtube.com/watch?v=kfDrGriS0vg&list=WL&index=1
        byte[] image = null;
        String imgName = null;
        try {
            Part imgPart = req.getPart("image");
            imgName = imgPart.getSubmittedFileName();

            if(imgName != null && !imgName.isEmpty()) {
                InputStream is = imgPart.getInputStream();
                image = new byte[is.available()];

                is.read(image);
            }
        } catch (Exception e) {
            results.put("imageError", "Something went wrong when trying to upload this image.");
//            results.put("imageError", e.getMessage());
        }

        if(image == null) {
            try {
                Build build = BuildDAO.get(buildName);
                image = build.getImage();
                results.put("base64Image", build.getBase64Image());
            } catch(Exception e) {
                results.put("imageError", "Please select an image");
            }
        }

        // Coordinates
        String xCoord = req.getParameter("xCoord");
        String yCoord = req.getParameter("yCoord");
        String zCoord = req.getParameter("zCoord");

        String coordinates = null;

        if(!xCoord.isEmpty() || !yCoord.isEmpty() || !zCoord.isEmpty()) { // if one isn't null
            if(xCoord.isEmpty() || yCoord.isEmpty() || zCoord.isEmpty()) { // if one isn't null but another one is
                results.put("coordError", "Either leave blank for fill out all values.");
            } else {
                coordinates = xCoord + ", " + yCoord + ", " + zCoord;
            }
        }

        // Tags
        String world = req.getParameter("world");
        String buildType = req.getParameter("buildType");

        results.put("buildName", buildName);
        results.put("description", description);
        results.put("dateBuilt", dateBuilt);
        results.put("world", world);
        results.put("buildType", buildType);
        results.put("xCoord", xCoord);
        results.put("yCoord", yCoord);
        results.put("zCoord", zCoord);

        // Input Validation
        if(buildName == null || buildName.isEmpty()) {
            results.put("buildNameError", "This new build must have a name.");
        }
        if(description == null || description.isEmpty()) {
            results.put("descriptionError", "Description is required. Ideas of what to include in description: build time, built by who, inspired by...");
        }
        if(world == null || world.isEmpty()) {
            results.put("worldError", "You must add at least one tag to this new build.");
        }

        if(buildType == null || buildType.isEmpty() || world == null || world.isEmpty()) {
            results.put("tagError", "You must set the build type and world tags.");
        }

        if (!results.containsKey("buildNameError") && !results.containsKey("descriptionError")
                && !results.containsKey("tagError") && !results.containsKey("coordError") && !results.containsKey("imageError") && !results.containsKey("dateBuildError")
        ) {
            try {
                Build build = new Build(buildName, userFromSession.getUserID(), image, world, buildType, dateBuiltDate, coordinates, Instant.now(), description);
                if(BuildDAO.edit(build)) {
                    session.setAttribute("flashMessageSuccess", "Building Successfully Edited!");
                    resp.sendRedirect("server-builds");
                    return;
                } else {
                    results.put("buildEditFail", "Failed to edit build");
                }
            } catch(RuntimeException e) {
                results.put("buildEditFail", "Failed to edit build" + e.getMessage());
            }
        }

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
        req.setAttribute("pageTitle", "Edit Build");
        req.getRequestDispatcher("WEB-INF/smp/edit-build.jsp").forward(req, resp);
    }
}
