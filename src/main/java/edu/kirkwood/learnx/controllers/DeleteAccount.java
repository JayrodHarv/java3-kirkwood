package edu.kirkwood.learnx.controllers;

import edu.kirkwood.learnx.data.UserDAO;
import edu.kirkwood.learnx.models.User;
import edu.kirkwood.shared.Helpers;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/delete-account")
public class DeleteAccount extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userFromSession = Helpers.getLearnXUserFromSession(session);
        if(userFromSession == null) {
            resp.sendRedirect("signin?redirect=delete-account");
            return;
        }
        req.setAttribute("pageTitle", "Delete Account");
        req.setAttribute("sideBarTab", "delete-account");
        req.getRequestDispatcher("WEB-INF/learnx/delete-account.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("emailInput");
        Map<String, String> results = new HashMap<>();

        results.clear();
        results.put("email", email);

        HttpSession session = req.getSession();
        User activeUser = (User)session.getAttribute("activeUser");
        if(!email.equals(activeUser.getEmail())) {
            results.put("emailError", "The value you entered is not the same as <b>'" + activeUser.getEmail() + "'</b>.");
        }

        if(!results.containsKey("emailError")) {
            UserDAO.delete(activeUser);
            session.invalidate();
            session = req.getSession();
            session.setAttribute("flashMessageWarning", "Your account has been deleted.");
            resp.sendRedirect("learnx");
            return;
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Delete Account");
        req.setAttribute("sideBarTab", "delete-account");
        req.getRequestDispatcher("WEB-INF/learnx/delete-account.jsp").forward(req, resp);
    }
}