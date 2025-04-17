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
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        Long uid = (Long) req.getSession().getAttribute("uid");

        try {
            DataMapper.delete(User.class, "uid = ?", uid);
            UserSession.DeleteSession(req, resp);
            out.println(Result.Ok());
        } catch (Exception e) {
            out.println(Result.Err(e.getMessage()));
        }
    }
}
