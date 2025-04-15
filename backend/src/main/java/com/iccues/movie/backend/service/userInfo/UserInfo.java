package com.iccues.movie.backend.service.userInfo;

import com.iccues.movie.backend.entities.Result;
import com.iccues.movie.backend.entities.user.User;
import com.iccues.movie.backend.utils.GsonProvider;
import com.iccues.movie.backend.utils.UserSession;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user_info/user_info")
public class UserInfo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        User user = UserSession.getUser(req);
        if (user != null) {
            out.println(GsonProvider.GSON.toJson(user));
        } else {
            out.println(Result.Err("User not logged in"));
        }
    }
}
