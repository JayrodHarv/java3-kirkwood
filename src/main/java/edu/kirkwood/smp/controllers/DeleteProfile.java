package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/smp-delete-profile")
public class DeleteProfile extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userID = req.getParameter("userID");
        if(userID != null && !userID.isEmpty()) {
            if(UserDAO.delete(userID)) {
                session.invalidate();
                session = req.getSession();
                session.setAttribute("flashMessageSuccess", "Profile Successfully Deleted");
                resp.sendRedirect("smp");
            } else {
                session.setAttribute("flashMessageDanger", "Failed to delete profile. Please try again.");
            }
        }
        resp.sendRedirect("smp-edit-profile?userID=" + userID);
    }
}
