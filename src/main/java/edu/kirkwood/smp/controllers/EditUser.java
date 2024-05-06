package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.UserDAO;
import edu.kirkwood.smp.models.Role;
import edu.kirkwood.smp.models.User;
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

@WebServlet("/smp-edit-uer")
public class EditUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> results = new HashMap<>();
        String userID = req.getParameter("userID");
        HttpSession session = req.getSession();
        UserVM userFromSession = (UserVM)session.getAttribute("activeSMPUser");
        if(userFromSession == null || !userFromSession.getStatus().equals("active")) {
            session.setAttribute("flashMessageWarning", "You must be logged in to view this page.");
            resp.sendRedirect("smp-login?redirect=smp-edit-user?userID=" + userID);
            return;
        }
        Role userRole = userFromSession.getRole();
        if(!userRole.canEditUsers()) {
            session.setAttribute("flashMessageWarning", "You are not allowed to view this page. Contact website owner to gain this power.");
            resp.sendRedirect("smp");
            return;
        }

        if(userID != null && !userID.isEmpty()) {
            results.put("userID", userID);
            try {
                User user = UserDAO.get(userID);
                if(user != null) {
                    results.put("roleID", user.getRole());
                } else {
                    session.setAttribute("flashMessageWarning", "Couldn't retrieve user data.");
                }
            } catch(Exception ex) {
                session.setAttribute("flashMessageDanger", "Something went wrong:\n" + ex.getMessage());
            }
        } else {
            session.setAttribute("flashMessageDanger", "No user id provided");
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Edit User");
        req.getRequestDispatcher("WEB-INF/smp/smp-edit-user.jsp").forward(req, resp);
    }
}
