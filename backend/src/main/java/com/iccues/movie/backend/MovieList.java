package com.iccues.movie.backend;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/movie_list")
public class MovieList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter out = resp.getWriter();
        out.println("""
                [
                  {
                    "title": "BanG Dream! Ave Mujica",
                    "id": 1
                  },
                  {
                    "title": "金牌得主 (舞冰的祈愿)",
                    "id": 2
                  },
                  {
                    "title": "群花绽放彷如修罗",
                    "id": 3
                  },
                  {
                    "title": "赛马娘 芦毛灰姑娘 Part.1",
                    "id": 4
                  }
                ]""");
    }
}
