package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.UserDAO;
import edu.kirkwood.smp.models.User;
import edu.kirkwood.shared.CommunicationService;
import edu.kirkwood.smp.models.UserVM;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/smp-signup")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,    // 1 MB
    maxFileSize = 1024 * 1024 * 10,     // 10 MB
    maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class Signup extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userFromSession = (User)session.getAttribute("activeSMPUser");
        if(userFromSession != null) {
            session.setAttribute("flashMessageWarning", "You are already logged in. Please sign out in order to create another account.");
            resp.sendRedirect("smp");
            return;
        }

        req.setAttribute("pageTitle", "Sign up for an account");
        req.getRequestDispatcher("WEB-INF/smp/smp-signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("inputEmail1");
        String password1 = req.getParameter("inputPassword1");
        String password2 = req.getParameter("inputPassword2");
        String displayName = req.getParameter("inputDisplayName");
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
        results.put("displayName", displayName);
        results.put("terms", terms[0]);

        User user = new User();
        try {
            user.setUserID(email);
        } catch (IllegalArgumentException e) {
            results.put("emailError", e.getMessage());
        }

        try {
            user.setDisplayName(displayName);
        } catch (IllegalArgumentException e) {
            results.put("displayNameError", e.getMessage());
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

        if(displayName == null || displayName.isEmpty()) {
            results.put("displayNameError", "You must enter a display name.");
        }

        // Using image retrieval found in this video
        // https://www.youtube.com/watch?v=kfDrGriS0vg&list=WL&index=1
        byte[] image = null;
        String imgName = null;
        try {
            Part imgPart = req.getPart("pfp");
            imgName = imgPart.getSubmittedFileName();

            if(imgName == null || imgName.isEmpty()) throw new IllegalArgumentException();

            InputStream is = imgPart.getInputStream();
            image = new byte[is.available()];

            is.read(image);
        } catch (Exception e) {
//            results.put("pfpError", "Something went wrong when trying to upload this profile picture.");
        }

        if(image == null) {
//            results.put("pfpError", "You must enter a profile picture.");
            try {
                // Java sucks
                String applicationPath = getServletContext().getRealPath("/");
                File f1 = new File(applicationPath);
                File f2 = new File(f1, "images");
                File f3 = new File(f2, "smp");
                File f4 = new File(f3, "Sample_User_Icon.png");
                String sampleImagePath = f4.getPath();
                System.out.println(sampleImagePath);
                image = Files.readAllBytes(f4.toPath());
                user.setPfp(image);
            } catch(Exception ex) {
                results.put("pfpError", "My code don't work." + ex.getMessage());
            }
        } else {
            user.setPfp(image);
        }

        if(terms == null || !terms[0].equals("agree")) {
            results.put("termsOfServiceError", "You must agree to our terms of service");
        }

        if (!results.containsKey("emailError") && !results.containsKey("password1Error")
                && !results.containsKey("password2Error") && !results.containsKey("termsOfServiceError")
                && !results.containsKey("displayNameError") && !results.containsKey("pfpError")
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

                    // Temp 2fa bypass
//                    String emailT = (String)session.getAttribute("email");
//                    user = UserDAO.get(email);
//                    user.setStatus("active");
//                    user.setRole("user");
//                    user.setLastLoggedIn(Instant.now().atOffset(ZoneOffset.UTC).toInstant());
//                    UserDAO.update(user);
//                    user.setPassword(null);
//                    session.removeAttribute("code");
//                    session.removeAttribute("email");
//
//                    session.setAttribute("activeSMPUser", user);
//                    session.setAttribute("flashMessageSuccess", "Welcome " + user.getDisplayName());
//                    resp.sendRedirect("smp");
                    return;
                }
                results.put("userAddSuccess", "User added");
            } catch (RuntimeException e) {
                results.put("userAddFail", "User not added" + e.getMessage());
            }
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Sign up for an account");
        req.getRequestDispatcher("WEB-INF/smp/smp-signup.jsp").forward(req, resp);
    }
}
