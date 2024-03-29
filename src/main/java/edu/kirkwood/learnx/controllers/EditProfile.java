package edu.kirkwood.learnx.controllers;

import edu.kirkwood.learnx.data.UserDAO;
import edu.kirkwood.learnx.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/edit-profile")
public class EditProfile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userFromSession = (User)session.getAttribute("activeUser");

        if(userFromSession == null) {
            session.setAttribute("flashMessageWarning", "You must be logged in to edit your profile");
            resp.sendRedirect("login?redirect=edit-profile");
            return;
        }
        req.setAttribute("pageTitle", "Edit Profile");
        req.getRequestDispatcher("WEB-INF/learnx/edit-profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstNameInput");
        String lastName = req.getParameter("lastNameInput");
        String language = req.getParameter("languageInput");

        Map<String, String> results = new HashMap<>();

        HttpSession session = req.getSession();
        User userFromSession = (User)session.getAttribute("activeUser");
        if(userFromSession != null) {
            userFromSession.setFirstName(firstName);
            userFromSession.setLastName(lastName);
            try {
                userFromSession.setLanguage(language);
            } catch (IllegalArgumentException e) {
                results.put("languageError", e.getMessage());
            }
            if(!results.containsKey("languageError")) {
                UserDAO.update(userFromSession);
                session.setAttribute("activeUser", userFromSession);
                session.setAttribute("flashMessageSuccess", "Your profile has been updated.");
            } else {
                session.setAttribute("flashMessageWarning", "Your profile was not updated.");
            }
        }
        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Edit Profile");
        req.getRequestDispatcher("WEB-INF/learnx/edit-profile.jsp").forward(req, resp);

    }
}