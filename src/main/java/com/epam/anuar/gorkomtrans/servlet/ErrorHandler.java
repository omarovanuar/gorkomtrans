package com.epam.anuar.gorkomtrans.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ErrorHandler extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Throwable throwable = (Throwable) req.getAttribute("javax.servlet.error.exception");
        String message = throwable.getMessage();
        Integer statusCode = (Integer) req.getAttribute("javax.servlet.error.status_code");
        String servletName = (String) req.getAttribute("javax.servlet.error.servlet_name");
        if (servletName == null) {
            servletName = "Unknown";
        }
        String requestUri = (String) req.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }

        req.setAttribute("exception", message);
        req.setAttribute("statusCode", statusCode);
        req.setAttribute("servletName", servletName);
        req.setAttribute("requestUri", requestUri);

        String path = "/WEB-INF/jsp/error-page.jsp";
        req.getRequestDispatcher(path).forward(req, resp);
    }
}
