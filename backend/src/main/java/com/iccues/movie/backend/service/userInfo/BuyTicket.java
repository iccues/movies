package com.iccues.movie.backend.service.userInfo;


import com.iccues.movie.backend.entities.Showtime;
import com.iccues.movie.backend.entities.Ticket;
import com.iccues.movie.backend.entities.Result;
import com.iccues.movie.backend.entities.user.User;
import com.iccues.movie.backend.utils.DataMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/user_info/buy_ticket")
public class BuyTicket extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String sid = req.getParameter("sid");
        Showtime showtime = DataMapper.selectFirst(Showtime.class, "sid = ?", sid);
        User user = (User) req.getSession().getAttribute("user");
        if(showtime != null) {
            try {
                showtime.bookedSeatsIncrease();
                Ticket ticket = new Ticket(user.getUid(), showtime.getSid());
                DataMapper.update(showtime);
                DataMapper.insert(ticket);
                out.println(Result.Ok());
            } catch (IllegalAccessException | SQLException e) {
                out.println(Result.Err(e.getMessage()));
            }
        }
    }
}
