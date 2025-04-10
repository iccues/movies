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
    private Long bookedSeats;

    // Info
    private String cinemaName;
    private String hallName;
}
