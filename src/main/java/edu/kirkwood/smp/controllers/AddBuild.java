package edu.kirkwood.smp.controllers;

import edu.kirkwood.learnx.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/add-build")
public class AddBuild extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User userFromSession = (User)session.getAttribute("activeSMPUser");

        String name = req.getParameter("name");

        // TODO: possibly add a way to add contributors and get the player head image using the Minecraft API
        String description = req.getParameter("description");
        // TODO: Image stuff that I don't know how to do yet
//        Building building = new Building(userFromSession.getId(), )
//        BuildingDAO.add(building);
    }
}
