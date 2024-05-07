package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.UserDAO;
import edu.kirkwood.smp.data.VoteOptionDAO;
import edu.kirkwood.smp.models.User;
import edu.kirkwood.smp.models.VoteOption;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/edit-vote-option")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,    // 1 MB
        maxFileSize = 1024 * 1024 * 10,     // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class EditVoteOption extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,String> results = new HashMap<>();
        HttpSession session = req.getSession();
        String optionID = req.getParameter("optionID");

        try {
            VoteOption option = VoteOptionDAO.get(Integer.parseInt(optionID));
            if(option != null) {
                req.setAttribute("option", option);
                results.put("base64Image", option.getBase64Image());
            } else {
                session.setAttribute("flashMessageWarning", "Failed to retrieve option data");
            }
        } catch(Exception ex) {
            session.setAttribute("flashMessageError", ex.getMessage());
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Edit Vote Option");
        req.getRequestDispatcher("WEB-INF/smp/edit-vote-option.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Map<String,String> results = new HashMap<>();
        String optionID = req.getParameter("optionID");
        String voteID = req.getParameter("voteID");
        String title = req.getParameter("title");
        String description = req.getParameter("description");


        // Using image retrieval found in this video
        // https://www.youtube.com/watch?v=kfDrGriS0vg&list=WL&index=1
        byte[] image = null;
        String imgName = null;
        try {
            Part imgPart = req.getPart("image");
            if(imgPart != null) {
                imgName = imgPart.getSubmittedFileName();

                if(imgName != null && !imgName.isEmpty()) {
                    InputStream is = imgPart.getInputStream();
                    image = new byte[is.available()];

                    is.read(image);
                }
            }
        } catch (Exception e) {
            results.put("imageError", "Something went wrong when trying to upload this image.");
        }

        VoteOption option = VoteOptionDAO.get(Integer.parseInt(optionID));
        if(image == null) {
            try {
                image = option.getImage();
                results.put("base64Image", option.getBase64Image());
            } catch(Exception e) {
                results.put("imageError", "Please select an image");
            }
        }

        // Input Validation
        if(title == null || title.isEmpty()) {
            results.put("titleError", "Vote options must have a title.");
        }
        if(description == null || description.isEmpty()) {
            results.put("descriptionError", "Please describe the this option so that a voter has the full picture.");
        }

        if (!results.containsKey("titleError") && !results.containsKey("descriptionError") && !results.containsKey("imageError")) {
            try {
                VoteOption option2 = new VoteOption(Integer.parseInt(optionID), voteID, title, description, image);
                if(VoteOptionDAO.edit(option2)) {
                    session.setAttribute("flashMessageSuccess", "Vote Option Successfully Edited!");
                    resp.sendRedirect("edit-vote?voteID=" + voteID);
                    return;
                } else {
                    session.setAttribute("flashMessageWarning", "Failed to edit vote option.");
                }
            } catch(RuntimeException e) {
                session.setAttribute("flashMessageError", "Failed to edit vote option." + e.getMessage());
            }
        }

        try {
            req.setAttribute("option", option);
            results.put("base64Image", option.getBase64Image());
        } catch(Exception ex) {
            session.setAttribute("flashMessageError", ex.getMessage());
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Edit Vote Option");
        req.getRequestDispatcher("WEB-INF/smp/edit-vote-option.jsp").forward(req, resp);
    }
}
