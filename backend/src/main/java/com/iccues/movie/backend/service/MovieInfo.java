package com.iccues.movie.backend.service;

import com.google.gson.Gson;
import com.iccues.movie.backend.entities.movie.MovieDetail;
import com.iccues.movie.backend.utils.DataMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/movie_info")
public class MovieInfo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String mid = req.getParameter("mid");

        PrintWriter out = resp.getWriter();
        out.println(new Gson().toJson(DataMapper.selectFirst(MovieDetail.class, "mid = " + mid)));
    }
}
