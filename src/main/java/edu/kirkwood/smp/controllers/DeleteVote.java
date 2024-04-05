package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.VoteDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/delete-vote")
public class DeleteVote extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String voteID = req.getParameter("voteID");

        if(voteID != null && !voteID.isEmpty()) {
            if(VoteDAO.delete(voteID)) {
                session.setAttribute("flashMessageSuccess", "Vote Successfully Deleted");
            } else {
                session.setAttribute("flashMessageDanger", "Failed to delete vote. Please try again.");
            }
        }
        resp.sendRedirect("votes");
    }
}
