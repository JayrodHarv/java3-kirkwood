package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.BuildTypeDAO;
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
import java.util.HashMap;
import java.util.Map;

@WebServlet("/add-build-type")
public class AddBuildType extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserVM userFromSession = (UserVM)session.getAttribute("activeSMPUser");
        if(userFromSession == null || !userFromSession.getStatus().equals("active")) {
            session.setAttribute("flashMessageWarning", "You must be logged in to view this page.");
            resp.sendRedirect("smp-login?redirect=add-build-type");
            return;
        }
        Role userRole = userFromSession.getRole();
        if(!userRole.canAddBuildTypes()) {
            session.setAttribute("flashMessageWarning", "You are not allowed to view this page. Contact website owner to gain this power.");
            resp.sendRedirect("smp");
            return;
        }

        req.setAttribute("pageTitle", "Add Build Type");
        req.getRequestDispatcher("WEB-INF/smp/add-build-type.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String buildTypeID = req.getParameter("buildTypeID");
        String description = req.getParameter("description");

        Map<String, String> results = new HashMap<>();

        results.put("buildTypeID", buildTypeID);
        results.put("description", description);

        if(buildTypeID.isEmpty()) {
            results.put("buildTypeError", "You must enter a name for the build type");
        }

        if(description.isEmpty()) {
            results.put("descriptionError", "You must enter a description");
        }

        if(!results.containsKey("buildTypeError") && !results.containsKey("descriptionError")) {
            try {
                BuildType buildType = new BuildType(buildTypeID, description);
                if(BuildTypeDAO.add(buildType)) {
                    session.setAttribute("flashMessageSuccess", "Successfully added new build type: " + buildTypeID);
                    resp.sendRedirect("smp-build-types");
                    return;
                } else {
                    session.setAttribute("flashMessageWarning", "Something went wrong while adding build type. Please try again.");
                }
            } catch (Exception ex) {
                session.setAttribute("flashMessageError", ex.getMessage());
            }
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Add Build Type");
        req.getRequestDispatcher("WEB-INF/smp/add-build-type.jsp").forward(req, resp);
    }
}
