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

import java.io.IOException;
import java.util.*;

@WebServlet("/server-builds")
public class ServerBuilds extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> results = new HashMap<>();
        int limit = 10;
        int offset = 0;
        String worldID = req.getParameter("worldID");
        String buildTypeID = req.getParameter("buildTypeID");
        String userFilter = "";

        if(worldID == null || worldID.isEmpty()) {
            worldID = "";
        }

        if(buildTypeID == null || buildTypeID.isEmpty()) {
            buildTypeID = "";
        }

        results.put("worldID", worldID);
        results.put("buildTypeID", buildTypeID);

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

        req.setAttribute("results", results);
        req.setAttribute("builds", builds);
        req.setAttribute("worlds", worlds);
        req.setAttribute("buildTypes", buildTypes);
        req.setAttribute("pageTitle", "Server Builds");
        req.getRequestDispatcher("WEB-INF/smp/server-builds.jsp").forward(req, resp);
    }
}
