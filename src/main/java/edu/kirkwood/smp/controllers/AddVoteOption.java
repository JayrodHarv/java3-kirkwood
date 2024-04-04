package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.BuildDAO;
import edu.kirkwood.smp.data.VoteOptionDAO;
import edu.kirkwood.smp.models.VoteOption;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/add-option")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,    // 1 MB
        maxFileSize = 1024 * 1024 * 10,     // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class AddVoteOption extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String voteID = req.getParameter("voteID");

        Map<String,String> results = new HashMap<>();

        results.put("voteID", voteID);

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Add Vote Option");
        req.getRequestDispatcher("WEB-INF/smp/add-option.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,String> results = new HashMap<>();
        String voteID = req.getParameter("voteID");
        String title = req.getParameter("title");
        String description = req.getParameter("description");


        // Using image retrieval found in this video
        // https://www.youtube.com/watch?v=kfDrGriS0vg&list=WL&index=1
        byte[] image = null;
        String imgName = null;
        try {
            Part imgPart = req.getPart("image");
            imgName = imgPart.getSubmittedFileName();

            if(imgName != null && !imgName.isEmpty()) {
                InputStream is = imgPart.getInputStream();
                image = new byte[is.available()];

                is.read(image);
            }
        } catch (Exception e) {
            results.put("imageError", "Something went wrong when trying to upload this image.");
        }

        // Input Validation
        if(title == null || title.isEmpty()) {
            results.put("titleError", "Vote options must have a title.");
        }
        if(description == null || description.isEmpty()) {
            results.put("descriptionError", "Please describe the this option so that a voter has the full picture.");
        }

        results.put("voteID", voteID);
        results.put("title", title);
        results.put("description", description);
        results.put("image", imgName);

        if (!results.containsKey("titleError") && !results.containsKey("descriptionError")
                && !results.containsKey("imageError")
        ) {
            try {
                VoteOption option = new VoteOption(voteID, title, description, image);
                if(VoteOptionDAO.add(option)) {
                    HttpSession session = req.getSession();
                    session.setAttribute("flashMessageSuccess", "Vote Option Successfully Added!");
                    resp.sendRedirect("edit-vote?voteID=" + voteID);
                    return;
                } else {
                    results.put("addOptionFail", "Failed to add vote option.");
                }
            } catch(RuntimeException e) {
                results.put("addOptionFail", "Failed to add vote option." + e.getMessage());
            }
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Add Vote Option");
        req.getRequestDispatcher("WEB-INF/smp/add-option.jsp").forward(req, resp);
    }
}
