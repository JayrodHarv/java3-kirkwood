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
import java.util.List;

@WebServlet("/smp-build-types")
public class BuildTypes extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserVM userFromSession = (UserVM)session.getAttribute("activeSMPUser");
        if(userFromSession == null || !userFromSession.getStatus().equals("active")) {
            session.setAttribute("flashMessageWarning", "You must be logged in to view this page.");
            resp.sendRedirect("smp-login?redirect=smp-build-types");
            return;
        }
        Role userRole = userFromSession.getRole();
        if(!userRole.canViewBuildTypes()) {
            session.setAttribute("flashMessageWarning", "You are not allowed to view this page. Contact website owner to gain this power.");
            resp.sendRedirect("smp");
            return;
        }

        try {
            List<BuildType> buildTypes = BuildTypeDAO.getAll();
            req.setAttribute("buildTypes", buildTypes);
        } catch(Exception ex) {
            session.setAttribute("flashMessageDanger", "Unable to retrieve list of build types:\n" + ex.getMessage());
        }

        req.setAttribute("pageTitle", "Build Types");
        req.getRequestDispatcher("WEB-INF/smp/smp-build-types.jsp").forward(req, resp);
    }
}
