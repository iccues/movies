package com.iccues.movie.backend.entities.movie;

import com.iccues.movie.backend.entities.Showtime;
import com.iccues.movie.backend.utils.DataMapper;
import com.iccues.movie.backend.utils.data.DataEntity;
import com.iccues.movie.backend.utils.data.Generated;
import com.iccues.movie.backend.utils.data.Key;
import com.iccues.movie.backend.utils.data.Table;

import java.sql.SQLException;
import java.util.List;

@DataEntity
@Table("movies")
public class MovieDetail {
    @Key
    @Generated
    private Long mid;
    private String title;
    private String introduction;

    public static void delete(String mid) throws SQLException {
        DataMapper.delete(MovieDetail.class, "mid = ?", mid);
        List<Showtime> showtimeList = DataMapper.selectAll(Showtime.class, "mid = ?", mid);
        for (Showtime showtime : showtimeList) {
            Showtime.delete(showtime.getSid().toString());
        }
    }
}
