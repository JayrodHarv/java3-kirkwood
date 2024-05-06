package edu.kirkwood.smp.controllers;

import edu.kirkwood.smp.data.WorldDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/delete-world")
public class DeleteWorld extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String worldID = req.getParameter("worldID");

        if(worldID != null && !worldID.isEmpty()) {
            if(WorldDAO.delete(worldID)) {
                session.setAttribute("flashMessageSuccess", "World successfully deleted");
            } else {
                session.setAttribute("flashMessageDanger", "Failed to delete world. Please try again.");
            }
        }
        resp.sendRedirect("smp-worlds");
    }
}
