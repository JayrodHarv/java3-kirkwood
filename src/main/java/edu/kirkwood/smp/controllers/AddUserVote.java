package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.VoteDAO;
import edu.kirkwood.smp.models.VoteVM;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/add-uservote")
public class AddUserVote extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        super.doPost(req, resp);
    }
}
