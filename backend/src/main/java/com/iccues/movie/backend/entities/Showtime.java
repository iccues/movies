package com.iccues.movie.backend.entities;

import com.iccues.movie.backend.utils.data.*;

import java.time.LocalDateTime;

@DataEntity
@Table("showtime")
public class Showtime {
    @Generated
    @Key
    private Long sid;
    private Long mid;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long price;
    private Long totalSeats;
    private Long bookedSeats = 0L;

    // Info
    private String cinemaName;
    private String hallName;

    public void bookedSeatsIncrease() {
        if (bookedSeats >= totalSeats) {
            throw new RuntimeException("Tickets sold out");
        }
        bookedSeats += 1;
    }

    public Long getSid() {
        return sid;
    }
    public Long getMid() {
        return mid;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public String getCinemaName() {
        return cinemaName;
    }
    public String getHallName() {
        return hallName;
    }
}
