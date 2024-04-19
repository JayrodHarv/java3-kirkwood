package edu.kirkwood.learnx.controllers;

import edu.kirkwood.learnx.data.UserDAO;
import edu.kirkwood.learnx.models.User;
import edu.kirkwood.shared.CommunicationService;
import edu.kirkwood.shared.Helpers;
import edu.kirkwood.shared.Validators;
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
import java.util.regex.Matcher;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("pageTitle", "Sign up for an account");
        req.getRequestDispatcher("WEB-INF/learnx/signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String email = req.getParameter("inputEmail1");
        String password1 = req.getParameter("inputPassword1");
        String password2 = req.getParameter("inputPassword2");
        String dateOfBirth = req.getParameter("date-of-birth");
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
        results.put("dateOfBirth", dateOfBirth);
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
        if(password2.equals("")) {
            results.put("password2Error", "This input is required");
        }
        if(!password1.equals(password2)) {
            results.put("password2Error", "Passwords don't match");
        }

        Matcher matcher = Validators.dateOfBirthPattern.matcher(dateOfBirth);
        if(!matcher.matches()) {
            results.put("dateOfBirthError", "Invalid date of birth");
        }
        if(Helpers.ageInYears(dateOfBirth) < 13) {
            results.put("dateOfBirthError", "You must be 13 years old or older to sign up");
        }

        if(terms == null || !terms[0].equals("agree")){
            results.put("termsOfServiceError", "You must agree to our terms of service");
        }

        if (!results.containsKey("emailError") && !results.containsKey("password1Error")
                && !results.containsKey("password2Error") && !results.containsKey("termsOfServiceError")
                && !results.containsKey("dateOfBirthError")
        ) {
            try {
                List<String> twoFactorInfo = UserDAO.add(user);
                if(!twoFactorInfo.isEmpty()) {
                    // Send user and email
                    String code = twoFactorInfo.get(0);
                    CommunicationService.sendNewUserEmail(code, email);
                    // Todo: display an error is the email cannot be sent
                    // Gets an existing session; Creates new one if doesn't exist

                    // Removes all existing session data if it exists
                    session.invalidate();
                    // Gets new session
                    session = req.getSession();
                    session.setAttribute("code", twoFactorInfo.get(0));
                    session.setAttribute("email", email);

                    //redirect to confirmation page
                    resp.sendRedirect("confirm");
                    return;
                }
                session.setAttribute("flashMessageSuccess", "User added");
            } catch (RuntimeException e) {
                session.setAttribute("flashMessageDanger", "User not added. \n" + e.getMessage());
            }
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Sign up for an account");
        req.getRequestDispatcher("WEB-INF/learnx/signup.jsp").forward(req, resp);
    }
}
