package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.UserDAO;
import edu.kirkwood.smp.data.WorldDAO;
import edu.kirkwood.smp.models.Role;
import edu.kirkwood.smp.models.User;
import edu.kirkwood.smp.models.UserVM;
import edu.kirkwood.smp.models.World;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/smp-worlds")
public class Worlds extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserVM userFromSession = (UserVM)session.getAttribute("activeSMPUser");
        if(userFromSession == null || !userFromSession.getStatus().equals("active")) {
            session.setAttribute("flashMessageWarning", "You must be logged in to view this page.");
            resp.sendRedirect("smp-login?redirect=smp-worlds");
            return;
        }
        Role userRole = userFromSession.getRole();
        if(!userRole.canViewWorlds()) {
            session.setAttribute("flashMessageWarning", "You are not allowed to view this page. Contact website owner to gain this power.");
            resp.sendRedirect("smp");
            return;
        }

        try {
            List<World> worlds = WorldDAO.getAll();
            req.setAttribute("worlds", worlds);
        } catch(Exception ex) {
            session.setAttribute("flashMessageDanger", "Unable to retrieve list of worlds:\n" + ex.getMessage());
        }

        req.setAttribute("pageTitle", "Worlds");
        req.getRequestDispatcher("WEB-INF/smp/smp-worlds.jsp").forward(req, resp);
    }
}
