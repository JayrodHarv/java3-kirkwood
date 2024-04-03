package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.VoteDAO;
import edu.kirkwood.smp.models.VoteListItemVM;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/votes")
public class Votes extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String,String> results = new HashMap<>();
        List<VoteListItemVM> votes = new ArrayList<>();
        try {
            votes = VoteDAO.getAll();
        } catch(Exception e) {
            results.put("getVoteListError", "Failed to retrieve votes.");
        }

        req.setAttribute("votes", votes);
        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Votes");
        req.getRequestDispatcher("WEB-INF/smp/votes.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
