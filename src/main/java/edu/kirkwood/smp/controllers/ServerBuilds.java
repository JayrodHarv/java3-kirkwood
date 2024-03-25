package edu.kirkwood.smp.controllers;

import edu.kirkwood.learnx.models.User;
import edu.kirkwood.smp.data.BuildingDAO;
import edu.kirkwood.smp.models.Building;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@WebServlet("/server-builds")
public class ServerBuilds extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Building> buildings = BuildingDAO.getAll();
        for(Building b : buildings) {
            req.setAttribute(b.getBuildingID() + "", "data:image/png;base64," + new String(Base64.getDecoder().decode(b.getImage())));
        }

        req.setAttribute("buildings", buildings);
        req.setAttribute("pageTitle", "Server Builds");
        req.getRequestDispatcher("WEB-INF/smp/server-builds.jsp").forward(req, resp);
    }
}
