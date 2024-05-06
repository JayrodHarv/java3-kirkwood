package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.VoteDAO;
import edu.kirkwood.smp.data.VoteOptionDAO;
import edu.kirkwood.smp.models.User;
import edu.kirkwood.smp.models.UserVM;
import edu.kirkwood.smp.models.UserVote;
import edu.kirkwood.smp.models.VoteVM;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/add-uservote")
public class AddUserVote extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserVM userFromSession = (UserVM)session.getAttribute("activeSMPUser");
        if(userFromSession == null || !userFromSession.getStatus().equals("active")) {
            session.setAttribute("flashMessageWarning", "You must be logged in to vote.");
            resp.sendRedirect("smp-login?redirect=votes");
            return;
        }

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
            results.put("getVoteError", "Failed to retrieve vote." + e.getMessage());
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Vote");
        req.getRequestDispatcher("WEB-INF/smp/add-uservote.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String voteID = req.getParameter("voteID");
        String selectedOption = req.getParameter("selectedOption");
        Map<String,String> results = new HashMap<>();

        results.put("voteID", voteID);
        results.put("selectedOption", selectedOption);

        if(selectedOption == null || selectedOption.isEmpty()) {
            results.put("selectionError", "You must select one of the listed options.");
        }

        if(!results.containsKey("selectionError")) {
            try {
                UserVM userFromSession = (UserVM)session.getAttribute("activeSMPUser");
                int selOp = Integer.parseInt(selectedOption);
                UserVote userVote = new UserVote(userFromSession.getUserID(), voteID, selOp, Instant.now());
                if(VoteDAO.addUserVote(userVote)) {
                    session.setAttribute("flashMessageSuccess", "Your vote has successfully been cast.");
                    resp.sendRedirect("votes");
                    return;
                } else {
                    session.setAttribute("flashMessageWarning", "Something went wrong when trying to cast your vote. Please try again.");
                }
            } catch(Exception e) {
                if(e.getMessage().contains("PRIMARY")) {
                    session.setAttribute("flashMessageWarning", "You can't vote for the same vote twice.");
                } else {
                    session.setAttribute("flashMessageDanger", "Failed to cast vote." + e.getMessage());
                }
                resp.sendRedirect("votes");
                return;
            }
        }

        try {
            VoteVM vote = VoteDAO.get(voteID);

            if(vote == null) throw new Exception();
            results.put("voteID", vote.getVoteID());
            results.put("userID", vote.getUserID());
            results.put("description", vote.getDescription());

            req.setAttribute("options", vote.getOptions());

        } catch(Exception e) {
            results.put("getVoteError", "Failed to retrieve vote." + e.getMessage());
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Vote");
        req.getRequestDispatcher("WEB-INF/smp/add-uservote.jsp").forward(req, resp);
    }
}
