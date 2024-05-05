package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.BuildTypeDAO;
import edu.kirkwood.smp.data.RoleDAO;
import edu.kirkwood.smp.models.BuildType;
import edu.kirkwood.smp.models.Role;
import edu.kirkwood.smp.models.UserVM;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/smp-roles")
public class Roles extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserVM userFromSession = (UserVM)session.getAttribute("activeSMPUser");
        if(userFromSession == null || !userFromSession.getStatus().equals("active")) {
            session.setAttribute("flashMessageWarning", "You must be logged in to view this page.");
            resp.sendRedirect("smp-login?redirect=smp-roles");
            return;
        }
        Role userRole = userFromSession.getRole();
        if(!userRole.canViewRoles()) {
            session.setAttribute("flashMessageWarning", "You are not allowed to view this page. Contact website owner to gain this power.");
            resp.sendRedirect("smp");
            return;
        }

        try {
            List<Role> roles = RoleDAO.getAll();
            req.setAttribute("roles", roles);
        } catch(Exception ex) {
            session.setAttribute("flashMessageDanger", "Unable to retrieve list of roles:\n" + ex.getMessage());
        }

        req.setAttribute("pageTitle", "Roles");
        req.getRequestDispatcher("WEB-INF/smp/smp-roles.jsp").forward(req, resp);
    }
}
