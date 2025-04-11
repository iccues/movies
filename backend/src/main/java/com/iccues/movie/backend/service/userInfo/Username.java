package com.iccues.movie.backend.service.userInfo;

import com.iccues.movie.backend.entities.result.Result;
import com.iccues.movie.backend.entities.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user_info/username")
public class Username extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        User user = (User) req.getSession().getAttribute("user");
        if (user != null) {
            out.println(Result.Success(user.getUsername()));
        }
    }
}
