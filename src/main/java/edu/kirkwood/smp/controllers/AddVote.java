package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.BuildDAO;
import edu.kirkwood.smp.data.BuildTypeDAO;
import edu.kirkwood.smp.data.VoteDAO;
import edu.kirkwood.smp.data.WorldDAO;
import edu.kirkwood.smp.models.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@WebServlet("/add-vote")
public class AddVote extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userFromSession = (User)session.getAttribute("activeSMPUser");
        if(userFromSession == null || !userFromSession.getStatus().equals("active")) {
            session.setAttribute("flashMessageWarning", "You must be logged in to create a new vote.");
            resp.sendRedirect("smp-login?redirect=add-vote");
            return;
        }

        req.setAttribute("pageTitle", "Add Vote");
        req.getRequestDispatcher("WEB-INF/smp/add-vote.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userFromSession = (User)session.getAttribute("activeSMPUser");
        Map<String, String> results = new HashMap<>();

        String voteName = req.getParameter("voteName");
        String description = req.getParameter("description");
        String startTime = req.getParameter("startTime");
        String endTime = req.getParameter("endTime");

        results.put("voteName", voteName);
        results.put("description", description);
        results.put("startTime", startTime);
        results.put("endTime", endTime);

        if(voteName == null || voteName.isEmpty()) {
            results.put("voteNameError", "You must enter a name to give to this vote.");
        } else {
            try {
                Vote vote = VoteDAO.get(voteName);
                if(vote != null) {
                    results.put("voteNameError", "There already exists a vote with this name. Please enter a different name.");
                }
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }

        if(description == null || description.isEmpty()) {
            results.put("descriptionError", "You must enter a description.");
        }

        Instant startDateTime = null;
        Instant endDateTime = null;
        if(startTime != null && !startTime.isEmpty() && endTime != null && !endTime.isEmpty()) {
            try {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
                startDateTime = formatter.parse(startTime).toInstant();
                endDateTime = formatter.parse(endTime).toInstant();
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            results.put("timePeriodError", "You must specify both a start time and an end time for this vote.");
        }

        if (!results.containsKey("voteNameError") && !results.containsKey("descriptionError")
                && !results.containsKey("timePeriodError")
        ) {
            try {
                Vote vote = new Vote(voteName, userFromSession.getUserID(), description, startDateTime, endDateTime);
                if(VoteDAO.add(vote)) {
                    resp.sendRedirect("edit-vote?voteId=" + voteName);
                    return;
                } else {
                    results.put("voteAddFail", "Failed to add vote");
                }
            } catch(RuntimeException e) {
                results.put("voteAddFail", "Failed to add vote" + e.getMessage());
                System.out.println(e.getMessage());
            }
        }


        req.setAttribute("results", results);
        req.setAttribute("pageTitle", "Add Vote");
        req.getRequestDispatcher("WEB-INF/smp/add-vote.jsp").forward(req, resp);
    }
}
