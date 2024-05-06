package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.BuildDAO;
import edu.kirkwood.smp.data.BuildTypeDAO;
import edu.kirkwood.smp.data.UserDAO;
import edu.kirkwood.smp.data.WorldDAO;
import edu.kirkwood.smp.models.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@WebServlet("/smp-edit-profile")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,    // 1 MB
        maxFileSize = 1024 * 1024 * 10,     // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class EditProfile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> results = new HashMap<>();
        String userID = req.getParameter("userID");
        HttpSession session = req.getSession();
        UserVM userFromSession = (UserVM)session.getAttribute("activeSMPUser");
        if(userFromSession == null || !userFromSession.getStatus().equals("active")) {
            session.setAttribute("flashMessageWarning", "You must be logged in to view this page.");
            resp.sendRedirect("smp-login?redirect=smp-edit-profile");
            return;
        }
        if(!userFromSession.getUserID().equals(userID)) {
            session.setAttribute("flashMessageWarning", "You can't edit another user's page.");
            resp.sendRedirect("smp");
            return;
        }

        try {
            User userFromDatabase = UserDAO.get(userID);
            if(userFromDatabase != null) {
                results.put("userID", userID);
                results.put("displayName", userFromDatabase.getDisplayName());
                results.put("base64Pfp", userFromDatabase.getBase64Pfp());
            } else {
                session.setAttribute("flashMessageWarning", "Unable to retrieve data for user.");
            }
        } catch(Exception ex) {
            session.setAttribute("flashMessageError", "Something went wrong while retrieving user data:\n" + ex.getMessage());
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Edit Profile");
        req.getRequestDispatcher("WEB-INF/smp/smp-edit-profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserVM userFromSession = (UserVM)session.getAttribute("activeSMPUser");

        Map<String, String> results = new HashMap<>();

        String userID = req.getParameter("userID");
        String displayName = req.getParameter("displayName");

        // Using image retrieval found in this video
        // https://www.youtube.com/watch?v=kfDrGriS0vg&list=WL&index=1
        byte[] image = null;
        String imgName = null;
        try {
            Part imgPart = req.getPart("pfp");
            if(imgPart != null) {
                imgName = imgPart.getSubmittedFileName();

                if(imgName != null && !imgName.isEmpty()) {
                    InputStream is = imgPart.getInputStream();
                    image = new byte[is.available()];

                    is.read(image);
                }
            }
        } catch (Exception e) {
            results.put("pfpError", "Something went wrong when trying to upload this image.");
//            results.put("imageError", e.getMessage());
        }

        User user = UserDAO.get(userID);
        if(image == null) {
            try {
                image = user.getPfp();
                results.put("base64Image", user.getBase64Pfp());
            } catch(Exception e) {
                results.put("pfpError", "Please select an image");
            }
        }

        results.put("userID", userID);
        results.put("displayName", displayName);

        // Input Validation
        if(displayName == null || displayName.isEmpty()) {
            results.put("displayNameError", "Display name is required.");
        }

        if (!results.containsKey("pfpError") && !results.containsKey("displayNameError")
        ) {
            try {
                user.setUserID(userID);
                user.setDisplayName(displayName);
                user.setPfp(image);
                if(UserDAO.update(user)) {
                    session.invalidate();
                    session = req.getSession();
                    UserVM userVM = UserDAO.getVM(userID);
                    session.setAttribute("activeSMPUser", userVM);
                    session.setAttribute("flashMessageSuccess", "Profile Successfully Edited!");
                } else {
                    session.setAttribute("flashMessageWarning", "Failed to edit profile");
                }
            } catch(RuntimeException e) {
                session.setAttribute("flashMessageDanger", "Failed to edit profile: \n" + e.getMessage());
            }
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Edit Profile");
        req.getRequestDispatcher("WEB-INF/smp/smp-edit-profile.jsp").forward(req, resp);
    }
}
