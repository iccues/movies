package com.iccues.movie.backend.controller.movie;

import com.iccues.movie.backend.entities.movie.MovieDetail;
import com.iccues.movie.backend.utils.DataMapper;
import com.iccues.movie.backend.utils.GsonProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/movie/movie_info")
public class MovieInfo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String mid = req.getParameter("mid");

        PrintWriter out = resp.getWriter();
        out.println(GsonProvider.GSON.toJson(DataMapper.selectFirst(MovieDetail.class, "mid = ?", mid)));
    }
}
