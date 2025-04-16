package com.iccues.movie.backend.entities.ticket;

import com.iccues.movie.backend.entities.Showtime;
import com.iccues.movie.backend.entities.movie.MovieSummary;
import com.iccues.movie.backend.utils.DataMapper;

import java.time.LocalDateTime;

public class TicketDetail {
    private String movieTitle;
    private String cinemaName;
    private String hallName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TicketDetail(Ticket ticket) {
        Showtime showtime = DataMapper.selectFirst(Showtime.class, "sid = ?", ticket.getSid());
        if (showtime != null) {
            MovieSummary movie = DataMapper.selectFirst(MovieSummary.class, "mid = ?", showtime.getMid());
            this.cinemaName = showtime.getCinemaName();
            this.hallName = showtime.getHallName();
            this.startTime = showtime.getStartTime();
            this.endTime = showtime.getEndTime();
            if (movie != null) {
                this.movieTitle = movie.getTitle();
            }
        }
    }
}
