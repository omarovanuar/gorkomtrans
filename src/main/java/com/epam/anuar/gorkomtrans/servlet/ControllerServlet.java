package com.epam.anuar.gorkomtrans.servlet;

import com.epam.anuar.gorkomtrans.dao.DaoFactory;
import com.epam.anuar.gorkomtrans.dao.UserDao;
import com.epam.anuar.gorkomtrans.db.ConnectionPool;
import com.epam.anuar.gorkomtrans.db.DbcpStart;
import com.epam.anuar.gorkomtrans.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
//
        DaoFactory daoFactory = DaoFactory.newInstance(poolAttribute);
        UserDao userDao = daoFactory.createUserDao();
        List<User> userList = new ArrayList<>();
        User user = new User(1, "gg-poster1@mail.ru", "user1", "pass1");
        userList.add(user);
        userList.add(new User(2, "user2", "pass2", "gg-poster2@mail.ru"));
        userList.add(new User(3, "user3", "pass3", "gg-poster3@mail.ru"));


        userDao.insert(user);
//        userDao.save(userList);

        HttpSession session = req.getSession();
        session.setAttribute("dbcp", result);

        String contextPath = getServletContext().getContextPath();
        resp.sendRedirect(contextPath + "/dbcp.jsp");

    }
}
