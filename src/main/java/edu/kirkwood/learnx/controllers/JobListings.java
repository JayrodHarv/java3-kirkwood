package edu.kirkwood.learnx.controllers;

import edu.kirkwood.learnx.data.CourseDAO;
import edu.kirkwood.learnx.data.JobListingDAO;
import edu.kirkwood.learnx.models.Course;
import edu.kirkwood.learnx.models.CourseCategory;
import edu.kirkwood.learnx.models.JobListing;
import edu.kirkwood.learnx.models.User;
import edu.kirkwood.shared.Helpers;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.TreeMap;

@WebServlet("/job-listings")
public class JobListings extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userFromSession = Helpers.getLearnXUserFromSession(session);
        int limit = 5;
        int offset = 0;
        String departmentName = req.getParameter("departmentName");
        String location = req.getParameter("location");
        // TODO: For pagination

        if(departmentName == null) {
            departmentName = "";
        }

        if(location == null) {
            location = "";
        }

        try {
            List<JobListing> jobListings = JobListingDAO.getAll(limit, offset, departmentName, location);
            req.setAttribute("jobListings", jobListings);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        req.setAttribute("pageTitle", "Job Listings");
        req.getRequestDispatcher("WEB-INF/learnx/job-listings.jsp").forward(req, resp);
    }
}
