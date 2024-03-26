package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.UserDAO;
import edu.kirkwood.smp.models.User;
import edu.kirkwood.shared.CommunicationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/smp-confirm")
public class Confirm2faCode extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String resend = req.getParameter("resend");
        if(resend != null) {
            HttpSession session = req.getSession();
            String code = (String)session.getAttribute("code");
            String email = (String)session.getAttribute("email");
            if(code != null && email != null) {
                boolean sent = CommunicationService.sendNewSMPUserEmail(code, email);
                if(sent) {
                    req.setAttribute("codeResent", "The code was resent");
                }
            }
        }
        req.setAttribute("pageTitle", "Confirm Your Code");
        req.getRequestDispatcher("WEB-INF/smp/smp-2fa-confirm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("inputCode");

        Map<String, String> results = new HashMap<>();
        results.put("code", code);

        HttpSession session = req.getSession();
        String codeFromSession = (String)session.getAttribute("code");
        // TODO: Check if the code is expired
        if(!code.equals(codeFromSession)) {
            //error
            results.put("codeError", "That code is incorrect");
        } else {
            String email = (String)session.getAttribute("email");
            User user = UserDAO.get(email);
            user.setStatus("active");
            user.setRole("user");
            user.setLastLoggedIn(Instant.now().atOffset(ZoneOffset.UTC).toInstant());
            UserDAO.update(user);
            user.setPassword(null);
            session.removeAttribute("code");
            session.removeAttribute("email");
            session.setAttribute("activeSMPUser", user);
            session.setAttribute("flashMessageSuccess", "Welcome new user");
            // add easter egg
            resp.sendRedirect("smp");
            return;
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Confirm Your Code");
        req.getRequestDispatcher("WEB-INF/smp/smp-2fa-confirm.jsp").forward(req, resp);
    }
}