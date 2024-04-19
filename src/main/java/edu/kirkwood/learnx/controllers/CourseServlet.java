package edu.kirkwood.learnx.controllers;

import edu.kirkwood.learnx.data.CourseDAO;
import edu.kirkwood.learnx.models.Course;
import edu.kirkwood.learnx.models.CourseCategory;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@WebServlet("/courses")
public class CourseServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userFromSession = Helpers.getLearnXUserFromSession(session);
        int limit = 5;
        int offset = 0;
        String[] categories = req.getParameterValues("category");
        String skillLevel = req.getParameter("skill-level");
        // TODO: For pagination

        String category = "";
        if(categories != null && categories.length > 0) {
            category = String.join(",", categories);
        }

        if(skillLevel == null) {
            skillLevel = "";
        }

        if(userFromSession != null) {
            try {
                TreeMap<Course, Instant> enrollments = CourseDAO.getCourseEnrollments(limit, offset, userFromSession.getId());
                req.setAttribute("enrollments", enrollments);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }

        List<Course> courses = CourseDAO.get(limit, offset, category, skillLevel);
        List<CourseCategory> categoriesDB = CourseDAO.getAllCategories();
        req.setAttribute("courses", courses);
        req.setAttribute("categories", categoriesDB);
        req.setAttribute("pageTitle", "Courses");
        req.setAttribute("categoryFilter", category);
        req.setAttribute("skillFilter", skillLevel);
        req.getRequestDispatcher("WEB-INF/learnx/all-courses.jsp").forward(req, resp);
    }
}