package com.iccues.movie.backend.controller.showtime;

import com.iccues.movie.backend.entities.Result;
import com.iccues.movie.backend.entities.Showtime;
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

@WebServlet("/showtime/add_showtime")
public class AddShowtime extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        if (!UserSession.isUserAdmin(req)) {
            out.println(Result.Err("User not logged in or not admin"));
            return;
        }


        try {
            Showtime showtime = GsonProvider.GSON.fromJson(req.getReader(), Showtime.class);
            DataMapper.insert(showtime);
            out.println(Result.Ok());
        } catch (Exception e) {
            out.println(Result.Err(e.getMessage()));
        }
    }
}
