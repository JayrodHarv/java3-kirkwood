package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.VoteOptionDAO;
import edu.kirkwood.smp.models.VoteOption;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/delete-vote-option")
public class DeleteVoteOption extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String optionID = req.getParameter("optionID");
        String voteID = req.getParameter("voteID");
        HttpSession session = req.getSession();

        if(optionID != null && !optionID.isEmpty()) {
            try {
                if (VoteOptionDAO.delete(Integer.parseInt(optionID))) {
                    session.setAttribute("flashMessageSuccess", "Vote Option Successfully Deleted");
                } else {
                    session.setAttribute("flashMessageWarning", "Failed to delete vote option. Please try again.");
                }
            } catch (Exception ex) {
                session.setAttribute("flashMessageDanger", "Couldn't delete vote option:\n" + ex.getMessage());
            }
        } else {
            session.setAttribute("flashMessageWarning", "No id provided...");
        }
        resp.sendRedirect("edit-vote?voteID=" + voteID);
    }
}
