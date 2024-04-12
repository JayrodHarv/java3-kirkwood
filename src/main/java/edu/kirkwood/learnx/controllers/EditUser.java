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

@WebServlet("/edit-user")
public class EditUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
//        User userFromSession = (User)session.getAttribute("activeUser");
//        if(userFromSession == null || !userFromSession.getStatus().equals("active") || !userFromSession.getPrivileges().equals("admin")) {
//            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
//            return;
//        }
        int userId = 0;
        try {
            userId = Integer.parseInt(req.getParameter("id"));
        } catch(NumberFormatException e) {
            resp.sendRedirect("all-users");
            return;
        }
        User userFromDatabase = UserDAO.get(userId);
        if(userFromDatabase != null) {
            req.setAttribute("user", userFromDatabase);
            String userName = "User";
            if(!userFromDatabase.getFirstName().equals("")) {
                userName = userFromDatabase.getFirstName();
            }
            if(!userFromDatabase.getLastName().equals("")) {
                userName += " " + userFromDatabase.getLastName();
            }
            req.setAttribute("pageTitle", "Edit " + userName);
            req.getRequestDispatcher("WEB-INF/learnx/edit-user.jsp").forward(req, resp);
        } else {
            session.setAttribute("flashMessageWarning", "No user found with that id");
            resp.sendRedirect("all-users");
            return;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = 0;
        try {
            userId = Integer.parseInt(req.getParameter("id"));
        } catch(NumberFormatException e) {
            resp.sendRedirect("all-users");
            return;
        }
        String firstNameInput = req.getParameter("firstNameInput");
        String lastNameInput = req.getParameter("lastNameInput");
        String emailInput = req.getParameter("emailInput");
        String phoneInput = req.getParameter("phoneInput");
        String languageInput = req.getParameter("languageInput");
        String statusInput = req.getParameter("statusInput");
        String privilegesInput = req.getParameter("privilegesInput");

        Map<String, String> results = new HashMap<>();
        User user = UserDAO.get(userId);
        if(user != null) {
            // todo: validate these strings
            user.setFirstName(firstNameInput);
            user.setLastName(lastNameInput);

            User userFromDatabase = UserDAO.get(emailInput);
            if(emailInput != null && !emailInput.equals(user.getEmail()) && userFromDatabase != null) {
                results.put("emailError", "A user with that email address already exists.");
            }
            if(!results.containsKey("emailError")) {
                try {
                    user.setEmail(emailInput);
                } catch (IllegalArgumentException e) {
                    results.put("emailError", e.getMessage());
                }
            }

            try {
                if (phoneInput != null && !phoneInput.equals("") && userFromDatabase != null && !phoneInput.equals(userFromDatabase.getPhone())) {
                    user.setPhone(phoneInput);
                }
            } catch (IllegalArgumentException e) {
                results.put("phoneError", e.getMessage());
            }

            try {
                user.setLanguage(languageInput);
            } catch (IllegalArgumentException e) {
                results.put("languageError", e.getMessage());
            }

            try {
                user.setStatus(statusInput);
            } catch (IllegalArgumentException e) {
                results.put("statusError", e.getMessage());
            }

            try {
                user.setPrivileges(privilegesInput);
            } catch (IllegalArgumentException e) {
                results.put("privilegesError", e.getMessage());
            }

            HttpSession session = req.getSession();
            if (!results.containsKey("firstNameError") && !results.containsKey("lastNameError")
                    && !results.containsKey("emailError") && !results.containsKey("phoneError")
                    && !results.containsKey("languageError") && !results.containsKey("statusError") && !results.containsKey("privilegesError")
            ) {
                UserDAO.update(user);
                session.setAttribute("flashMessageSuccess", "User updated");
            } else {
                session.setAttribute("flashMessageWarning", "Please check your inputs");
            }


        } else {
            resp.sendRedirect("all-users");
            return;
        }

        req.setAttribute("user", user);
        String userName = "User";
        if(!user.getFirstName().equals("")) {
            userName = user.getFirstName();
        }
        if(!user.getLastName().equals("")) {
            userName += " " + user.getLastName();
        }
        req.setAttribute("pageTitle", "Edit " + userName);
        req.getRequestDispatcher("WEB-INF/learnx/edit-user.jsp").forward(req, resp);


    }
}
