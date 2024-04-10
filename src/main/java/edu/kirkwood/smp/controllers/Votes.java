package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.VoteDAO;
import edu.kirkwood.smp.models.User;
import edu.kirkwood.smp.models.VoteVM;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/votes")
public class Votes extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = req.getParameter("page");
        Map<String,String> results = new HashMap<>();
        List<VoteVM> votes = new ArrayList<>();

        if(page == null || page.isEmpty()) {
            page = "active";
        }

        try {
            switch (page) {
                case "active":
                    votes = VoteDAO.getActive();
                    break;
                case "myVotes":
                    HttpSession session = req.getSession();
                    User userFromSession = (User)session.getAttribute("activeSMPUser");
                    if(userFromSession == null || !userFromSession.getStatus().equals("active")) {
                        session.setAttribute("flashMessageWarning", "You must be logged in to view your votes.");
                        resp.sendRedirect("smp-login?redirect=votes");
                        return;
                    }
                    votes = VoteDAO.getMyVotes(userFromSession.getUserID());
                    break;
                case "concluded":
                    votes = VoteDAO.getConcluded();
                    break;
            }
        } catch(Exception e) {
            results.put("getVoteListError", "Failed to retrieve votes.");
        }

        results.put("page", page);

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
