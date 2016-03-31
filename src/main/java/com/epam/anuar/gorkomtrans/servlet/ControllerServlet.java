package com.epam.anuar.gorkomtrans.servlet;

import com.epam.anuar.gorkomtrans.dao.DaoFactory;
import com.epam.anuar.gorkomtrans.dao.DaoService;
import com.epam.anuar.gorkomtrans.dao.UserDao;
import com.epam.anuar.gorkomtrans.db.ConnectionPool;
import com.epam.anuar.gorkomtrans.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


        DaoFactory daoFactory = DaoFactory.newInstance();
        UserDao userDao = daoFactory.createUserDao();
        //create
//        List<User> userList = new ArrayList<>();
//        User user = new User(6, "NEWgg-poster1@mail.ru", "NEWuser1", "NEWpass1");
//        userList.add(new User(2, "gg-poster2@mail.ru", "user2", "pass2"));
//        userList.add(new User(3, "gg-poster3@mail.ru", "user3", "pass3"));

//        userDao.insert(user);
//        userDao.save(userList);

        //read
//        Map<String, String> params = new HashMap<>();
//        params.put("ID", "1");
//        params.put("LOGIN", "user1");
//        params.put("PASSWORD", "pass1");

//        User userById = userDao.findById(3);
//        User userByLogin = userDao.findByLogin("user2");
//        User userByAllParameters = userDao.findByAllParameters(params);
//        List<User> allUsers = userDao.findAll();

//        HttpSession session = req.getSession();
//        session.setAttribute("userById", userById.getLogin() + " " + userById.getEmail());
//        session.setAttribute("userByLogin", userByLogin.getLogin() + " " + userByLogin.getEmail());
//        session.setAttribute("userByAllParameters", userByAllParameters.getLogin() + " " + userByAllParameters.getEmail());
//        session.setAttribute("allUsers", DaoService.viewAllUsers(allUsers));

        //update
//        userDao.update(user);
//        userDao.update(userList);

        //delete
        User user = new User();
        user.setEmail("NEWgg-poster1@mail.ru");
        userDao.delete(user);
//        userDao.delete(userList);

        String contextPath = getServletContext().getContextPath();
        resp.sendRedirect(contextPath + "/dbcp.jsp");

    }
}
