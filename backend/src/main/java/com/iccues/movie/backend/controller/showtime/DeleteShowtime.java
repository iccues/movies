package com.iccues.movie.backend.controller.showtime;

import com.iccues.movie.backend.entities.Result;
import com.iccues.movie.backend.entities.Showtime;
import com.iccues.movie.backend.entities.ticket.Ticket;
import com.iccues.movie.backend.utils.DataMapper;
import com.iccues.movie.backend.utils.UserSession;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/showtime/delete")
public class DeleteShowtime extends HttpServlet {
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        if (!UserSession.isUserAdmin(req)) {
            out.println(Result.Err("User not logged in or not admin"));
            return;
        }

        String sid = req.getParameter("sid");
        try {
            Showtime.delete(sid);
            out.println(Result.Ok());
        } catch (Exception e) {
            out.println(Result.Err(e.getMessage()));
        }
    }
}
