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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/smp-signup")
public class Signup extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("pageTitle", "Sign up for an account");
        req.getRequestDispatcher("WEB-INF/smp/smp-signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("inputEmail1");
        String password1 = req.getParameter("inputPassword1");
        String password2 = req.getParameter("inputPassword2");
        String[] terms = req.getParameterValues("checkbox-1");
        if(terms == null) {
            terms = new String[] {"0"};
        }

        if(password1 == null) {
            password1 = "";
        }

        Map<String, String> results = new HashMap<>();
        results.put("email", email);
        results.put("password1", password1);
        results.put("password2", password2);
        results.put("terms", terms[0]);

        User user = new User();
        try {
            user.setEmail(email);
        } catch (IllegalArgumentException e) {
            results.put("emailError", e.getMessage());
        }

        User userFromDB = UserDAO.get(email);
        if(userFromDB != null) {
            results.put("emailError", "A user with that email address already exists.");
        }

        try {
            user.setPassword(password1.toCharArray());
        } catch(IllegalArgumentException e) {
            results.put("password1Error", e.getMessage());
        }
        if(password1.isEmpty()) {
            results.put("password1Error", "This input is required");
        }
        if(password2.isEmpty()) {
            results.put("password2Error", "This input is required");
        }
        if(!password1.equals(password2)) {
            results.put("password2Error", "Passwords don't match");
        }
        if(terms == null || !terms[0].equals("agree")) {
            results.put("termsOfServiceError", "You must agree to our terms of service");
        }

        if (!results.containsKey("emailError") && !results.containsKey("password1Error")
                && !results.containsKey("password2Error") && !results.containsKey("termsOfServiceError")
        ) {
            try {
                List<String> twoFactorInfo = UserDAO.add(user);
                if(!twoFactorInfo.isEmpty()) {
                    // Send user and email
                    String code = twoFactorInfo.get(0);
                    CommunicationService.sendNewSMPUserEmail(code, email);
                    // Todo: display an error is the email cannot be sent
                    // Gets an existing session; Creates new one if doesn't exist
                    HttpSession session = req.getSession();
                    // Removes all existing session data if it exists
                    session.invalidate();
                    // Gets new session
                    session = req.getSession();
                    session.setAttribute("code", twoFactorInfo.get(0));
                    session.setAttribute("email", email);

                    //redirect to confirmation page
                    resp.sendRedirect("smp-confirm");
                    return;
                }
                results.put("userAddSuccess", "User added");
            } catch (RuntimeException e) {
                results.put("userAddFail", "User not added");
            }
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Sign up for an account");
        req.getRequestDispatcher("WEB-INF/smp/smp-signup.jsp").forward(req, resp);
    }
}
