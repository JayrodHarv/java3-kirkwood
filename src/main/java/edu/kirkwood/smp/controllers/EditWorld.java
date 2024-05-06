package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.WorldDAO;
import edu.kirkwood.smp.models.Role;
import edu.kirkwood.smp.models.UserVM;
import edu.kirkwood.smp.models.World;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@WebServlet("/edit-world")
public class EditWorld extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> results = new HashMap<>();
        HttpSession session = req.getSession();
        String worldID = req.getParameter("worldID");
        UserVM userFromSession = (UserVM)session.getAttribute("activeSMPUser");
        if(userFromSession == null || !userFromSession.getStatus().equals("active")) {
            session.setAttribute("flashMessageWarning", "You must be logged in to view this page.");
            resp.sendRedirect("smp-login?redirect=edit-world?worldID=" + worldID);
            return;
        }
        Role userRole = userFromSession.getRole();
        if(!userRole.canEditWorlds()) {
            session.setAttribute("flashMessageWarning", "You are not allowed to view this page. Contact website owner to gain this power.");
            resp.sendRedirect("smp");
            return;
        }

        try {
            World world = WorldDAO.get(worldID);
            req.setAttribute("world", world);
        } catch(Exception ex) {
            session.setAttribute("flashMessageError", "Something went wrong while retrieving world data:\n" + ex.getMessage());
        }

        req.setAttribute("pageTitle", "Edit World");
        req.getRequestDispatcher("WEB-INF/smp/edit-world.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> results = new HashMap<>();
        HttpSession session = req.getSession();
        String oldWorldID = req.getParameter("oldWorldID");
        String worldID = req.getParameter("worldID");
        String description = req.getParameter("description");

        // Date Started
        String dateStarted = req.getParameter("dateStarted");
        Date dateStartedDate = null;
        if(dateStarted != null && !dateStarted.isEmpty()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            try {
                dateStartedDate = formatter.parse(dateStarted);
            } catch (ParseException e) {
                results.put("dateStartedError", "Date entered was incorrect or in the wrong format.");
            }
        }

        if(worldID.isEmpty()) {
            results.put("worldIDError", "You must enter a name for the world");
        }

        if(description.isEmpty()) {
            results.put("descriptionError", "You must enter a description");
        }

        if(!results.containsKey("worldIDError") && !results.containsKey("descriptionError") && !results.containsKey("dateStartedError")) {
            try {
                World world = new World(worldID, dateStartedDate, description);
                if(WorldDAO.edit(world, oldWorldID)) {
                    session.setAttribute("flashMessageSuccess", "Successfully edited world: " + worldID);
                    resp.sendRedirect("smp-worlds");
                    return;
                } else {
                    session.setAttribute("flashMessageWarning", "Something went wrong while editing world. Please try again.");
                }
            } catch (Exception ex) {
                session.setAttribute("flashMessageError", ex.getMessage());
            }
        }

        try {
            World world = WorldDAO.get(oldWorldID);
            req.setAttribute("world", world);
        } catch(Exception ex) {
            session.setAttribute("flashMessageError", "Something went wrong while retrieving world data:\n" + ex.getMessage());
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Edit World");
        req.getRequestDispatcher("WEB-INF/smp/edit-world.jsp").forward(req, resp);
    }
}
