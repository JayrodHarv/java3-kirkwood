package edu.kirkwood.demos;

import edu.kirkwood.shared.CommunicationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;

@WebServlet("/email")
public class EmailServlet extends HttpServlet {
    private static HashMap<String, String> results = new HashMap<>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/demos/email.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String subject = req.getParameter("subject");
        String message = req.getParameter("message");

        results.clear();
        CommunicationService.sendEmail(email, subject, message);

        results.put("message", "Email Sent");
        req.setAttribute("results", results);
        req.getRequestDispatcher("WEB-INF/demos/email.jsp").forward(req, resp);
    }
}
