package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.BuildTypeDAO;
import edu.kirkwood.smp.models.BuildType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/edit-build-type")
public class EditBuildType extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String buildTypeID = req.getParameter("buildTypeID");

        Map<String, String> results = new HashMap<>();

        try {
            BuildType buildType = BuildTypeDAO.get(buildTypeID);
            results.put("buildTypeID", buildType.getBuildTypeID());
            results.put("description", buildType.getDescription());
        } catch(Exception e) {
            session.setAttribute("flashMessageError", "Something went wrong while retrieving build type data: " + e.getMessage());
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Edit Build Type");
        req.getRequestDispatcher("WEB-INF/smp/edit-build-type.jsp").forward(req, resp);
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
                if(BuildTypeDAO.edit(buildType)) {
                    session.setAttribute("flashMessageSuccess", "Successfully made changes on build type: " + buildTypeID);
                    resp.sendRedirect("smp-build-types");
                    return;
                } else {
                    session.setAttribute("flashMessageWarning", "Something went wrong while updating build type. Please try again.");
                }
            } catch (Exception ex) {
                session.setAttribute("flashMessageError", ex.getMessage());
            }
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Edit Build Type");
        req.getRequestDispatcher("WEB-INF/smp/edit-build-type.jsp").forward(req, resp);
    }
}
