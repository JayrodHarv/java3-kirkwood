package edu.kirkwood.learnx.controllers;

import edu.kirkwood.learnx.data.UserDAO;
import edu.kirkwood.learnx.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/all-users")
public class AllUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        HttpSession session = req.getSession();
//        User userFromSession = (User)session.getAttribute("activeUser");
//        if(userFromSession == null || !userFromSession.getStatus().equals("active") || !userFromSession.getPrivileges().equals("admin")) {
//            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
//            return;
//        }
        List<User> users = UserDAO.getAll();
        req.setAttribute("users", users);
        req.setAttribute("pageTitle", "All Users");
        req.getRequestDispatcher("WEB-INF/learnx/all-users.jsp").forward(req,resp);

    }
}
