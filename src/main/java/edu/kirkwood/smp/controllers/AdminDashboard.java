package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.BuildTypeDAO;
import edu.kirkwood.smp.data.UserDAO;
import edu.kirkwood.smp.data.VoteDAO;
import edu.kirkwood.smp.data.WorldDAO;
import edu.kirkwood.smp.models.BuildType;
import edu.kirkwood.smp.models.User;
import edu.kirkwood.smp.models.VoteVM;
import edu.kirkwood.smp.models.World;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/smp-admin-dashboard")
public class AdminDashboard extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userFromSession = (User)session.getAttribute("activeSMPUser");
        if(userFromSession == null || !userFromSession.getStatus().equals("active") || !userFromSession.getRole().equals("admin")) {
            session.setAttribute("flashMessageWarning", "You must be logged in as an admin to view this page.");
            resp.sendRedirect("smp-login?redirect=smp-admin-dashboard");
            return;
        }

        String page = req.getParameter("page");
        Map<String,String> results = new HashMap<>();

        if(page == null || page.isEmpty()) {
            page = "smp-users";
        }

        try {
            switch (page) {
                case "smp-users":
                    List<User> users = UserDAO.getAll();
                    req.setAttribute("users", users);
                    break;
                case "worlds":
                    List<World> worlds = WorldDAO.getAll();
                    req.setAttribute("worlds", worlds);
                    break;
                case "build-types":
                    List<BuildType> buildTypes = BuildTypeDAO.getAll();
                    req.setAttribute("buildTypes", buildTypes);
                    break;
            }
        } catch(Exception e) {
            session.setAttribute("flashMessageDanger", "Failed to retrieve content." + "\n" + e.getMessage());
        }

        results.put("page", page);

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Admin Dashboard");
        req.getRequestDispatcher("WEB-INF/smp/smp-admin-dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
