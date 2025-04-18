package com.iccues.movie.backend.controller.movie;

import com.iccues.movie.backend.entities.Result;
import com.iccues.movie.backend.entities.movie.MovieDetail;
import com.iccues.movie.backend.utils.DataMapper;
import com.iccues.movie.backend.utils.UserSession;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/movie/delete")
public class DeleteMovie extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        if (!UserSession.isUserAdmin(req)) {
            out.println(Result.Err("User not logged in or not admin"));
            return;
        }

        String mid = req.getParameter("mid");
        try {
            MovieDetail.delete(mid);
            out.println(Result.Ok());
        } catch (SQLException e) {
            out.println(Result.Err(e.getMessage()));
        }
    }
}
