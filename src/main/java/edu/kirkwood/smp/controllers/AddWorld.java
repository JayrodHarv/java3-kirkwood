package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.BuildTypeDAO;
import edu.kirkwood.smp.data.WorldDAO;
import edu.kirkwood.smp.models.BuildType;
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

@WebServlet("/add-world")
public class AddWorld extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserVM userFromSession = (UserVM)session.getAttribute("activeSMPUser");
        if(userFromSession == null || !userFromSession.getStatus().equals("active")) {
            session.setAttribute("flashMessageWarning", "You must be logged in to view this page.");
            resp.sendRedirect("smp-login?redirect=add-world");
            return;
        }
        Role userRole = userFromSession.getRole();
        if(!userRole.canAddWorlds()) {
            session.setAttribute("flashMessageWarning", "You are not allowed to view this page. Contact website owner to gain this power.");
            resp.sendRedirect("smp");
            return;
        }

        req.setAttribute("pageTitle", "Add World");
        req.getRequestDispatcher("WEB-INF/smp/add-world.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> results = new HashMap<>();
        HttpSession session = req.getSession();
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

        results.put("worldID", worldID);
        results.put("dateStarted", dateStarted);
        results.put("description", description);

        if(worldID.isEmpty()) {
            results.put("worldIDError", "You must enter a name for the world");
        }

        if(description.isEmpty()) {
            results.put("descriptionError", "You must enter a description");
        }

        if(!results.containsKey("worldIDError") && !results.containsKey("descriptionError") && !results.containsKey("dateStartedError")) {
            try {
                World world = new World(worldID, dateStartedDate, description);
                if(WorldDAO.add(world)) {
                    session.setAttribute("flashMessageSuccess", "Successfully added new world: " + worldID);
                    resp.sendRedirect("smp-worlds");
                    return;
                } else {
                    session.setAttribute("flashMessageWarning", "Something went wrong while adding world. Please try again.");
                }
            } catch (Exception ex) {
                session.setAttribute("flashMessageError", ex.getMessage());
            }
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Add New World");
        req.getRequestDispatcher("WEB-INF/smp/add-world.jsp").forward(req, resp);
    }
}
