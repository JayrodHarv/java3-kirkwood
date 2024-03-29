package edu.kirkwood.learnx.controllers;

import edu.kirkwood.learnx.data.CourseDAO;
import edu.kirkwood.learnx.models.Course;
import edu.kirkwood.learnx.models.CourseCategory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/courses")
public class CourseServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryFilter = "";
        String skillFilter = "";
        // TODO: For pagination
        int limit = 5;
        int offset = 0;
        List<Course> courses = CourseDAO.get(limit, offset, categoryFilter, skillFilter);
        List<CourseCategory> categories = CourseDAO.getAllCategories();
        req.setAttribute("courses", courses);
        req.setAttribute("categories", categories);
        req.setAttribute("pageTitle", "Courses");
        req.getRequestDispatcher("WEB-INF/learnx/all-courses.jsp").forward(req, resp);
    }
}