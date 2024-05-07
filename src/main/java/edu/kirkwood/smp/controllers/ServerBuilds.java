package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.BuildTypeDAO;
import edu.kirkwood.smp.data.WorldDAO;
import edu.kirkwood.smp.models.BuildType;
import edu.kirkwood.smp.models.BuildVM;
import edu.kirkwood.smp.data.BuildDAO;
import edu.kirkwood.smp.models.World;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.*;

@WebServlet("/server-builds")
public class ServerBuilds extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Map<String, String> results = new HashMap<>();
        String worldID = req.getParameter("worldID");
        String buildTypeID = req.getParameter("buildTypeID");
        String userFilter = "";

        // pagination
        int limit = 4; // limit of 4 builds per page


        String pageStr = req.getParameter("page");
        int page = 1;
        try {
            page = Integer.parseInt(pageStr);
        } catch(NumberFormatException ex) {
            session.setAttribute("flashMessageError", "Invalid page number. Showing first page of content");
        }

        if(page < 1) {
            page = 1;
        }

        req.setAttribute("page", page);

        int offset = (page - 1) * limit;


        if(worldID == null || worldID.isEmpty()) {
            worldID = "";
        }

        if(buildTypeID == null || buildTypeID.isEmpty()) {
            buildTypeID = "";
        }

        int numberOfBuilds = BuildDAO.getBuildCount(worldID, buildTypeID, userFilter);
        int numberOfPages = numberOfBuilds / limit;
        if(numberOfBuilds % limit != 0) {
            numberOfPages++;
        }

        int pageLinks = 5;
        int beginPage = page / pageLinks * pageLinks > 0 ? page / pageLinks * pageLinks : 1;
        int endPage = beginPage + pageLinks - 1 > numberOfPages ? numberOfPages : beginPage + pageLinks - 1;

        results.put("worldID", worldID);
        results.put("buildTypeID", buildTypeID);
        results.put("numberOfBuilds", numberOfBuilds + "");

        List<BuildVM> builds = new ArrayList<>();
        List<World> worlds = new ArrayList<>();
        List<BuildType> buildTypes = new ArrayList<>();
        try {
            builds = BuildDAO.getAll(limit, offset, worldID, buildTypeID, userFilter);
            worlds = WorldDAO.getAll();
            buildTypes = BuildTypeDAO.getAll();
        } catch(Exception e) {
            results.put("getBuildListError", "Failed to retrieve the list of buildings.");
        }

        req.setAttribute("beginPage", beginPage);
        req.setAttribute("endPage", endPage);
        req.setAttribute("numberOfPages", numberOfPages);
        req.setAttribute("results", results);
        req.setAttribute("builds", builds);
        req.setAttribute("worlds", worlds);
        req.setAttribute("buildTypes", buildTypes);
        req.setAttribute("pageTitle", "Server Builds");
        req.getRequestDispatcher("WEB-INF/smp/server-builds.jsp").forward(req, resp);
    }
}
