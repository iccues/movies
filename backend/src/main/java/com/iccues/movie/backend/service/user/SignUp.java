package com.iccues.movie.backend.service.user;

import com.google.gson.Gson;
import com.iccues.movie.backend.entities.result.Result;
import com.iccues.movie.backend.entities.user.User;
import com.iccues.movie.backend.entities.user.RowUser;
import com.iccues.movie.backend.utils.DataMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/signup")
public class SignUp extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        RowUser rowUser = new Gson().fromJson(req.getReader(), RowUser.class);
        User user = rowUser.intoHashedUser();
        try {
            DataMapper.insert(user);
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            out.println(Result.Success(null));
        } catch (IllegalAccessException | SQLException e) {
            out.println(Result.Failure("Sign up failed"));
        }
    }
}
