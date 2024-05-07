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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/edit-vote")
public class EditVote extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String voteID = req.getParameter("voteID");
        Map<String,String> results = new HashMap<>();
        try {
            VoteVM vote = VoteDAO.get(voteID);

            if(vote == null) throw new Exception();
            results.put("voteID", vote.getVoteID());
            results.put("userID", vote.getUserID());
            results.put("description", vote.getDescription());

            req.setAttribute("options", vote.getOptions());

        } catch(Exception e) {
            session.setAttribute("flashMessageError", "Failed to retrieve vote." + e.getMessage());
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Edit Vote");
        req.getRequestDispatcher("WEB-INF/smp/edit-vote.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String oldVoteID = req.getParameter("oldVoteID");
        String voteID = req.getParameter("voteID");
        String description = req.getParameter("description");
        Map<String,String> results = new HashMap<>();

        results.put("voteID", voteID);
        results.put("description", description);

        try {
            Vote vote = VoteDAO.get(oldVoteID);
            vote.setVoteID(voteID);
            vote.setDescription(description);
            if(VoteDAO.edit(vote, oldVoteID)) {
                session.setAttribute("flashMessageSuccess", "Successfully edited vote.");
            } else {
                session.setAttribute("flashMessageWarning", "Something went wrong while editing vote.");
            }
        } catch(Exception ex) {
            session.setAttribute("flashMessageDanger", "Something went wrong while editing vote:\n" + ex.getMessage());
        }

        try {
            VoteVM vote = VoteDAO.get(voteID);

            if(vote == null) throw new Exception();
            results.put("voteID", vote.getVoteID());
            results.put("userID", vote.getUserID());
            results.put("description", vote.getDescription());

            req.setAttribute("options", vote.getOptions());

        } catch(Exception e) {
            session.setAttribute("flashMessageError", "Failed to retrieve vote." + e.getMessage());
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Edit Vote");
        req.getRequestDispatcher("WEB-INF/smp/edit-vote.jsp").forward(req, resp);
    }
}
