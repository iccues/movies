package com.iccues.movie.backend.service.user;

import com.google.gson.Gson;
import com.iccues.movie.backend.entities.Result;
import com.iccues.movie.backend.entities.Status;
import com.iccues.movie.backend.entities.user.RowUser;
import com.iccues.movie.backend.entities.user.User;
import com.iccues.movie.backend.utils.DataMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LogIn extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        RowUser rowUser = new Gson().fromJson(req.getReader(), RowUser.class);
        User user = DataMapper.selectFirst(User.class, "username = '" + rowUser.getUsername() + "'");
        if (user != null && user.checkPassword(rowUser)) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            out.println(Result.Success(null));
        }
        else {
            out.println(Result.Failure("Username or password is incorrect"));
        }
    }
}
