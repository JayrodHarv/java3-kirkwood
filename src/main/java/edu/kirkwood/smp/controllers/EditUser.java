package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.RoleDAO;
import edu.kirkwood.smp.data.UserDAO;
import edu.kirkwood.smp.models.Role;
import edu.kirkwood.smp.models.User;
import edu.kirkwood.smp.models.UserVM;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/smp-edit-user")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,    // 1 MB
        maxFileSize = 1024 * 1024 * 10,     // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class EditUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> results = new HashMap<>();
        String userID = req.getParameter("userID");
        HttpSession session = req.getSession();
        UserVM userFromSession = (UserVM)session.getAttribute("activeSMPUser");
        if(userFromSession == null || !userFromSession.getStatus().equals("active")) {
            session.setAttribute("flashMessageWarning", "You must be logged in to view this page.");
            resp.sendRedirect("smp-login?redirect=smp-edit-user?userID=" + userID);
            return;
        }
        Role userRole = userFromSession.getRole();
        if(!userRole.canEditUsers()) {
            session.setAttribute("flashMessageWarning", "You are not allowed to view this page. Contact website owner to gain this power.");
            resp.sendRedirect("smp");
            return;
        }

        if(userID != null && !userID.isEmpty()) {
            results.put("userID", userID);
            try {
                User user = UserDAO.get(userID);
                if(user != null) {
                    results.put("displayName", user.getDisplayName());
                    results.put("base64Pfp", user.getBase64Pfp());
                    results.put("roleID", user.getRole());
                    try {
                        req.setAttribute("roles", RoleDAO.getAll());
                    } catch(Exception ex) {
                        session.setAttribute("flashMessageError", "Failed to retrieve roles:\n" + ex.getMessage());
                    }
                } else {
                    session.setAttribute("flashMessageWarning", "Couldn't retrieve user data.");
                }
            } catch(Exception ex) {
                session.setAttribute("flashMessageDanger", "Something went wrong:\n" + ex.getMessage());
            }
        } else {
            session.setAttribute("flashMessageDanger", "No user id provided");
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Edit User");
        req.getRequestDispatcher("WEB-INF/smp/smp-edit-user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Map<String, String> results = new HashMap<>();

        String userID = req.getParameter("userID");
        String displayName = req.getParameter("displayName");
        String roleID = req.getParameter("roleID");

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
        results.put("roleID", roleID);

        // Input Validation
        if(displayName == null || displayName.isEmpty()) {
            results.put("displayNameError", "Display name is required.");
        }

        if(roleID == null || roleID.isEmpty()) {
            results.put("roleError", "Role is required.");
        }

        if (!results.containsKey("pfpError") && !results.containsKey("displayNameError") && !results.containsKey("roleError")) {
            try {
                user.setUserID(userID);
                user.setDisplayName(displayName);
                user.setPfp(image);
                user.setRole(roleID);
                if(UserDAO.update(user)) {
                    session.setAttribute("flashMessageSuccess", "User Successfully Edited!");
                    resp.sendRedirect("smp-users");
                    return;
                } else {
                    session.setAttribute("flashMessageWarning", "Failed to edit user");
                }
            } catch(RuntimeException e) {
                session.setAttribute("flashMessageDanger", "Failed to edit user: \n" + e.getMessage());
            }
        }

        try {
            if(user != null) {
                try {
                    req.setAttribute("roles", RoleDAO.getAll());
                } catch(Exception ex) {
                    session.setAttribute("flashMessageError", "Failed to retrieve roles:\n" + ex.getMessage());
                }
            } else {
                session.setAttribute("flashMessageWarning", "Couldn't retrieve user data.");
            }
        } catch(Exception ex) {
            session.setAttribute("flashMessageDanger", "Something went wrong:\n" + ex.getMessage());
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Edit User");
        req.getRequestDispatcher("WEB-INF/smp/smp-edit-user.jsp").forward(req, resp);
    }
}
