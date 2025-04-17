package com.iccues.movie.backend.controller.user;

import com.iccues.movie.backend.entities.Result;
import com.iccues.movie.backend.entities.user.User;
import com.iccues.movie.backend.utils.DataMapper;
import com.iccues.movie.backend.utils.UserSession;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/user/delete_account")
public class DeleteAccount extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        User user = UserSession.getUser(req);
        if (user == null) {
            out.println(Result.Err("User not logged in"));
            return;
        }

        try {
            DataMapper.delete(user);
            UserSession.DeleteSession(req, resp);
            out.println(Result.Ok());
        } catch (IllegalAccessException | SQLException e) {
            out.println(Result.Err(e.getMessage()));
        }
    }
}
