package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.VoteDAO;
import edu.kirkwood.smp.data.VoteOptionDAO;
import edu.kirkwood.smp.models.Vote;
import edu.kirkwood.smp.models.VoteOption;
import edu.kirkwood.smp.models.VoteVM;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.TemporalUnit;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/start-vote")
public class StartVote extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String voteID = req.getParameter("voteID");
        Map<String,String> results = new HashMap<>();
        HttpSession session = req.getSession();

        try {
            VoteVM vote = VoteDAO.get(voteID);
            if(vote == null) throw new Exception();

            if(vote.getNumberOfOptions() < 2) {
                session.setAttribute("flashMessageWarning", "Vote must have 2 or more options in order to begin voting. Please add more options.");
                resp.sendRedirect("edit-vote?voteID=" + voteID);
                return;
            }
        } catch(Exception e) {
            results.put("getVoteError", "Failed to find vote with the name " + voteID);
        }

        results.put("voteID", voteID);

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Start Vote");
        req.getRequestDispatcher("WEB-INF/smp/start-vote.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String voteID = req.getParameter("voteID");
        String duration = req.getParameter("duration");
        String timeUnit = req.getParameter("timeUnit");
        Map<String,String> results = new HashMap<>();

        results.put("voteID", voteID);
        results.put("duration", duration);
        results.put("timeUnit", timeUnit);

        // Input Validation
        if(duration == null || duration.isEmpty() || duration.equals("0")) {
            results.put("durationError", "You must enter a duration that you would like this vote to last before voting is closed. Value cannot be 0.");
        }
        if(timeUnit == null || timeUnit.isEmpty()) {
            results.put("timeUnitError", "You must select a unit of time that you want the duration to be in.");
        }

        if (!results.containsKey("durationError") && !results.containsKey("timeUnitError")) {
            try {
                Vote newVote = VoteDAO.get(voteID);
                Instant startTime = Instant.now();
                // calculate endTime
                Instant endTime = null;
                int durationInt = Integer.parseInt(duration);
                switch (timeUnit) {
                    case "minutes":
                        endTime = startTime.plusSeconds(durationInt * 60L);
                        break;
                    case "hours":
                        endTime = startTime.plusSeconds(durationInt * 60 * 60L);
                        break;
                    case "days":
                        endTime = startTime.plusSeconds(durationInt * 60 * 60 * 24L);
                        break;
                }

                newVote.setStartTime(startTime);
                newVote.setEndTime(endTime);
                if(VoteDAO.edit(newVote)) {
                    HttpSession session = req.getSession();
                    session.setAttribute("flashMessageSuccess", "Voting has started for " + voteID);
                    resp.sendRedirect("votes");
                    return;
                } else {
                    results.put("startVoteError", "Failed to start vote.");
                }
            } catch(RuntimeException e) {
                results.put("startVoteError", "Failed to start vote." + e.getMessage());
            }
        }

        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Start Vote");
        req.getRequestDispatcher("WEB-INF/smp/start-vote.jsp").forward(req, resp);
    }
}
