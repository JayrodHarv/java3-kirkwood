package edu.kirkwood.learnx.controllers;

import edu.kirkwood.learnx.data.CourseDAO;
import edu.kirkwood.learnx.models.User;
import edu.kirkwood.shared.Helpers;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/enroll")
public class Enrollment extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userFromSession = Helpers.getLearnXUserFromSession(session);
        if(userFromSession == null || !Helpers.isStudent(userFromSession)) {
            resp.sendRedirect("courses");
            return;
        }
        String courseIdStr = req.getParameter("course");
        int courseId = 0;
        try {
            courseId = Integer.parseInt(courseIdStr);
        } catch(NumberFormatException e) {
            resp.sendRedirect("courses");
            return;
        }
        if(CourseDAO.enroll(userFromSession.getId(), courseId)) {
            session.setAttribute("flashMessageSuccess", "You're enrolled");
        } else {
            session.setAttribute("flashMessageDanger", "Enrollment Failed");
        }
        resp.sendRedirect("student");
    }
}
