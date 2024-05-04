package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.BuildTypeDAO;
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
            if(BuildTypeDAO.delete(buildTypeID)) {
                session.setAttribute("flashMessageSuccess", "Build type successfully deleted");
            } else {
                session.setAttribute("flashMessageDanger", "Failed to delete build type. Please try again.");
            }
        }
        resp.sendRedirect("smp-admin-dashboard?page=build-types");
    }
}
