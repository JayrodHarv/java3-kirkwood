package edu.kirkwood.shared;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/errorHandler")
public class ErrorHandler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorCode = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString();
        String exceptionType = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString();
        String errorMessage = req.getAttribute(RequestDispatcher.ERROR_MESSAGE).toString();

        String result = "Error code: " + errorCode;
        if (!exceptionType.equals("")) {
            result += "\nException: " + exceptionType;
        }
        result += "\nMessage: " + errorMessage;
        System.err.println(result);
        req.setAttribute("pageTitle", "Error");
        req.getRequestDispatcher("WEB-INF/shared/error.jsp").forward(req, resp);
    }
}
