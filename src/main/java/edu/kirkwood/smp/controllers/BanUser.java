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

@WebServlet("/ban-user")
public class BanUser extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userID = req.getParameter("userID");
        HttpSession session = req.getSession();
        UserVM userFromSession = (UserVM)session.getAttribute("activeSMPUser");
        if(userFromSession == null || !userFromSession.getStatus().equals("active")) {
            session.setAttribute("flashMessageWarning", "You must be logged in to view this page.");
            resp.sendRedirect("smp-login?redirect=ban-user?userID=" + userID);
            return;
        }
        Role userRole = userFromSession.getRole();
        if(!userRole.canBanUsers()) {
            session.setAttribute("flashMessageWarning", "You are not allowed to view this page. Contact website owner to gain this power.");
            resp.sendRedirect("smp");
            return;
        }

        if(userID != null && !userID.isEmpty()) {
            try {
                User userFromDatabase = UserDAO.get(userID);
                if(userFromDatabase != null) {
                    userFromDatabase.setStatus("locked");
                    if(UserDAO.update(userFromDatabase)) {
                        session.setAttribute("flashMessageSuccess", "Successfully banned this user from the website.");
                    } else {
                        session.setAttribute("flashMessageDanger", "Something went wrong while trying to ban user. Please try again");
                    }
                } else {
                    session.setAttribute("flashMessageWarning", "Couldn't find user with email of " + userID);
                }
            } catch(Exception ex) {
                session.setAttribute("flashMessageDanger", "Something went wrong while trying to ban user:\n" + ex.getMessage());
            }
        }
        resp.sendRedirect("smp-users");
        return;
    }
}
