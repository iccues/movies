package com.iccues.movie.backend.controller.ticket;


import com.iccues.movie.backend.entities.Showtime;
import com.iccues.movie.backend.entities.ticket.Ticket;
import com.iccues.movie.backend.entities.Result;
import com.iccues.movie.backend.entities.user.User;
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

@WebServlet("/ticket/buy_ticket")
public class BuyTicket extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String sid = req.getParameter("sid");
        Showtime showtime = DataMapper.selectFirst(Showtime.class, "sid = ?", sid);
        User user = UserSession.getUser(req);
        if(showtime != null && user != null) {
            try {
                showtime.bookedSeatsIncrease();
                Ticket ticket = new Ticket(user.getUid(), showtime.getSid());
                DataMapper.update(showtime);
                DataMapper.insert(ticket);
                out.println(Result.Ok());
            } catch (IllegalAccessException | SQLException e) {
                out.println(Result.Err(e.getMessage()));
            }
        } else {
            out.println(Result.Err("User not logged in"));
        }
    }
}
