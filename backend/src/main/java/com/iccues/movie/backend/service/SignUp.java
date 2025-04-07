package com.iccues.movie.backend.service;

import com.google.gson.Gson;
import com.iccues.movie.backend.entities.user.User;
import com.iccues.movie.backend.entities.user.RowUser;
import com.iccues.movie.backend.utils.DataMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/sign_up")
public class SignUp extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RowUser rowUser = new Gson().fromJson(req.getReader(), RowUser.class);
        User user = rowUser.intoHashedUser();
        try {
            DataMapper.insert(user);
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
