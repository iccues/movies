package com.iccues.movie.backend.controller.user;

import com.google.gson.JsonObject;
import com.iccues.movie.backend.entities.Result;
import com.iccues.movie.backend.entities.user.User;
import com.iccues.movie.backend.utils.DataMapper;
import com.iccues.movie.backend.utils.GsonProvider;
import com.iccues.movie.backend.utils.UserSession;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/user/change_password")
public class ChangePassword extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        JsonObject obj = GsonProvider.GSON.fromJson(req.getReader(), JsonObject.class);
        String oldPassword = obj.get("oldPassword").getAsString();
        String newPassword = obj.get("newPassword").getAsString();

        User user = UserSession.getUser(req);
        if (user == null) {
            out.println(Result.Err("User not logged in"));
            return;
        }
        if (!user.checkPassword(oldPassword)) {
            out.println(Result.Err("Old password does not match"));
            return;
        }
        try {
            user.setPassword(newPassword);
            DataMapper.update(user);
            out.println(Result.Ok());
        } catch (IllegalAccessException | SQLException e) {
            out.println(Result.Err(e.getMessage()));
        }
    }
}
