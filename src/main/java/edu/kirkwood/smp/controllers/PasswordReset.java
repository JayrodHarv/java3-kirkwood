package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/smp-password-reset")
public class PasswordReset extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("pageTitle", "Reset your password");
        req.getRequestDispatcher("WEB-INF/smp/smp-password-reset.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String email = req.getParameter("inputEmail1");
        Map<String, String> results = new HashMap<>();
        results.put("email", email);

        //TODO: write an if statement if the email was sent
        UserDAO.passwordReset(email, req);
        session.setAttribute("flashMessageWarning", "If there is an account found, we will send an email");

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Reset your password");
        req.getRequestDispatcher("WEB-INF/smp/smp-password-reset.jsp").forward(req, resp);
    }
}
