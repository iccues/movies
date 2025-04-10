package com.iccues.movie.backend.entities;

import com.iccues.movie.backend.utils.data.*;

import java.sql.Time;

@DataEntity
@Table("showtime")
public class Showtime {
    @Generated
    @Key
    private Long sid;
    private Long mid;
    private Time startTime;

}
