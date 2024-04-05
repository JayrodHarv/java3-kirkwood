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