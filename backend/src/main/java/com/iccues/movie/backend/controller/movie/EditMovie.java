package com.iccues.movie.backend.controller.movie;

import com.iccues.movie.backend.entities.Result;
import com.iccues.movie.backend.entities.movie.MovieDetail;
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

@WebServlet("/movie/edit_movie")
public class EditMovie extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        if (!UserSession.isUserAdmin(req)) {
            out.println(Result.Err("User not logged in or not admin"));
            return;
        }

        MovieDetail movieDetail = GsonProvider.GSON.fromJson(req.getReader(), MovieDetail.class);
        try {
            DataMapper.update(movieDetail);
            out.println(Result.Ok());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            out.println(Result.Err(e.getMessage()));
        }
    }
}
