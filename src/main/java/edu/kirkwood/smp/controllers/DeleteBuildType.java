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

@WebServlet("/delete-build-type")
public class DeleteBuildType extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String buildTypeID = req.getParameter("buildTypeID");

        if(buildTypeID != null && !buildTypeID.isEmpty()) {
            try {
                if(BuildTypeDAO.delete(buildTypeID)) {
                    session.setAttribute("flashMessageSuccess", "Build type successfully deleted");
                } else {
                    session.setAttribute("flashMessageWarning", "Couldn't delete build type. Please try again.");
                }
            } catch(Exception ex) {
                session.setAttribute("flashMessageDanger", "Couldn't delete build type:\n" + ex.getMessage());
            }
        } else {
            session.setAttribute("flashMessageWarning", "No id provided...");
        }
        resp.sendRedirect("smp-build-types");
    }
}
