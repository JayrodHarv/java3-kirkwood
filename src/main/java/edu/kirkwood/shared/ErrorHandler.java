package edu.kirkwood.shared;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/errorHandler")
public class ErrorHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorCode = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) + "";
        String exceptionType = req.getAttribute(RequestDispatcher.ERROR_EXCEPTION) + "";
        String errorMessage = req.getAttribute(RequestDispatcher.ERROR_MESSAGE) + "";
        String servlet = req.getAttribute(RequestDispatcher.ERROR_SERVLET_NAME) + "";

        String result = "Error code: " + errorCode;
        if (!exceptionType.equals("")) {
            result += "<br>\nException: " + exceptionType;
        }
        result += "<br>\nMessage: " + errorMessage;
        result += "<br>\nServlet: " + servlet;
        System.err.println(result);

        if(!errorCode.equals("404")) {
            CommunicationService.sendEmail(Dotenv.load().get("ADMIN_EMAIL"), "Unknown error occured", result);
        }

        req.setAttribute("pageTitle", "Error");
        req.getRequestDispatcher("WEB-INF/shared/error.jsp").forward(req, resp);
    }
}
