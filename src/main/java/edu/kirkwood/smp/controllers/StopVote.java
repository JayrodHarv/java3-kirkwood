package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.VoteDAO;
import edu.kirkwood.smp.models.Vote;
import edu.kirkwood.smp.models.VoteVM;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.Instant;

@WebServlet("/stop-vote")
public class StopVote extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String voteID = req.getParameter("voteID");

        try {
            Vote vote = VoteDAO.get(voteID);
            Instant stopTime = Instant.now();

            vote.setEndTime(stopTime);

            if(VoteDAO.edit(vote)) {
                session.setAttribute("flashMessageSuccess", "Voting has ended for " + voteID);
            } else {
                session.setAttribute("flashMessageDanger", "Failed to end voting for " + voteID);
            }
        } catch(RuntimeException e) {
            session.setAttribute("flashMessageDanger", "Failed to end voting for " + voteID + ".\n" + e.getMessage());
        }

        resp.sendRedirect("votes");
    }
}
