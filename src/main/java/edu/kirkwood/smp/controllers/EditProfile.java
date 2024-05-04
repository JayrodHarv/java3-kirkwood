package edu.kirkwood.smp.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/smp-edit-profile")
public class EditProfile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("pageTitle", "Edit Profile");
        req.getRequestDispatcher("WEB-INF/smp/smp-edit-profile.jsp").forward(req, resp);
    }
}
