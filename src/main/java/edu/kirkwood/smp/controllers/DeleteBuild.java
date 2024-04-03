package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.BuildDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/delete-build")
public class DeleteBuild extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String buildID = req.getParameter("buildID");

        if(buildID != null && !buildID.isEmpty()) {
            if(BuildDAO.delete(buildID)) {
                session.setAttribute("flashMessageSuccess", "Build Successfully Deleted");
                resp.sendRedirect("server-builds");
            }
        } else {
            session.setAttribute("flashMessageDanger", "Failed to delete build. Please try again.");
        }
    }
}
