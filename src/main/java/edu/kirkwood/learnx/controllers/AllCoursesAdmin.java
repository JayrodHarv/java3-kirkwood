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
import java.util.List;
import java.util.TreeMap;

@WebServlet("/all-courses-admin")
public class AllCoursesAdmin extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userFromSession = Helpers.getLearnXUserFromSession(session);
        int limit = 5;
        int offset = 0;
        // TODO: For pagination

        List<Course> courses = CourseDAO.get(limit, offset, "", "");
        req.setAttribute("courses", courses);
        req.setAttribute("pageTitle", "Courses");
        req.getRequestDispatcher("WEB-INF/learnx/all-courses-admin.jsp").forward(req, resp);
    }
}
