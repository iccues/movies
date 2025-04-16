package com.iccues.movie.backend.controller.ticket;

import com.iccues.movie.backend.entities.ticket.Ticket;
import com.iccues.movie.backend.entities.ticket.TicketDetail;
import com.iccues.movie.backend.entities.user.User;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ticket/ticket_detail_list")
public class TicketDetailList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        User user = UserSession.getUser(req);

        PrintWriter out = resp.getWriter();
        if (user != null) {
            List<Ticket> tickets = DataMapper.selectAll(Ticket.class, "uid = ?", user.getUid());
            List<TicketDetail> ticketDetails = new ArrayList<>();
            for (Ticket ticket : tickets) {
                ticketDetails.add(new TicketDetail(ticket));
            }
            out.println(GsonProvider.GSON.toJson(ticketDetails));
        }
    }
}
