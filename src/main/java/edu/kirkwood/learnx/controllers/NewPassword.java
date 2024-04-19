package edu.kirkwood.learnx.controllers;

import edu.kirkwood.learnx.data.UserDAO;
import edu.kirkwood.learnx.models.User;
import edu.kirkwood.shared.CommunicationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/new-password")
public class NewPassword extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        String tempEmail = UserDAO.getPasswordReset(token);
        if(token == null || tempEmail.equals("")) {
            resp.sendRedirect("learnx");
            return;
        }
        HttpSession session = req.getSession();
        session.setAttribute("tempEmail", tempEmail);
        req.setAttribute("pageTitle", "New Password");
        req.getRequestDispatcher("WEB-INF/learnx/new-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password1 = req.getParameter("inputPassword1");
        String password2 = req.getParameter("inputPassword2");
        Map<String, String> results = new HashMap<>();
        results.put("password1", password1);
        results.put("password2", password2);

        User user = new User();
        try {
            user.setPassword(password1.toCharArray());
        } catch(IllegalArgumentException e) {
            results.put("password1Error", e.getMessage());
        }
        if(password2.equals("")) {
            results.put("password2Error", "This input is required");
        }
        if(!password1.equals(password2)) {
            results.put("password2Error", "Passwords don't match");
        }

        if (!results.containsKey("password1Error")
                && !results.containsKey("password2Error")
        ) {
            try {
                HttpSession session = req.getSession();
                boolean resetComplete = UserDAO.resetPassword((String)session.getAttribute("tempEmail"), password1);
                // Send user an email

                if(resetComplete) {
                    session.setAttribute("flashMessageSuccess", "Your password has been reset. Please login");
                    resp.sendRedirect("login");
                    return;
                } else {
                    req.setAttribute("flashMessageDanger", "Failed to change password.");
                }

            } catch (Exception ex) {
                req.setAttribute("flashMessageDanger", "Failed to change password.\n" + ex.getMessage());
            }
            // Todo: Display error saying "Could not add user" if twoFactorInfo is empty
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "New Password");
        req.getRequestDispatcher("WEB-INF/learnx/new-password.jsp").forward(req, resp);
    }
}