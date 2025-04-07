package com.iccues.movie.backend.service;

import com.google.gson.Gson;
import com.iccues.movie.backend.entities.MovieSummary;
import com.iccues.movie.backend.utils.DataMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

@WebServlet("/movie_list")
public class MovieList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        out.println(new Gson().toJson(DataMapper.selectAll(MovieSummary.class)));
    }
}
