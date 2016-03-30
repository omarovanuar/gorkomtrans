package com.epam.anuar.gorkomtrans.servlet;

import com.epam.anuar.gorkomtrans.db.ConnectionPool;
import com.epam.anuar.gorkomtrans.db.DbcpStart;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/testing")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String name = req.getParameter("login");
//        String greeting = "Hello " + name;
//        req.setAttribute("greeting", greeting);
//        RequestDispatcher dispatcher = req.getRequestDispatcher("greeting.jsp");
//        dispatcher.forward(req, resp);
//
//        HttpSession session = req.getSession();
//        session.setAttribute("greeting", greeting);
//
//        String contextPath = getServletContext().getContextPath();
//        resp.sendRedirect(contextPath + "/greeting.jsp");

        ConnectionPool poolAttribute = (ConnectionPool) getServletContext().getAttribute("db.pool");
        String result = DbcpStart.start(poolAttribute);

        HttpSession session = req.getSession();
        session.setAttribute("dbcp", result);

        String contextPath = getServletContext().getContextPath();
        resp.sendRedirect(contextPath + "/dbcp.jsp");

    }
}
