package edu.kirkwood.learnx.controllers;

import edu.kirkwood.learnx.data.CourseDAO;
import edu.kirkwood.learnx.models.Course;
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
import java.util.Map;

@WebServlet("/student")
public class Student extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userFromSession = Helpers.getLearnXUserFromSession(session);
        if(userFromSession == null || !Helpers.isStudent(userFromSession)) {
            session.setAttribute("flashMessageWarning", "You must be a student to view this content.");
            resp.sendRedirect("signin?redirect=student");
            return;
        }

        int limit = 5;
        int offset = 0;
        Map<Course, Instant> enrollments = CourseDAO.getCourseEnrollments(limit, offset, userFromSession.getId());

        req.setAttribute("enrollments", enrollments);
        req.setAttribute("pageTitle", "Student Dashboard");
        req.getRequestDispatcher("WEB-INF/learnx/student.jsp").forward(req, resp);
    }
}
