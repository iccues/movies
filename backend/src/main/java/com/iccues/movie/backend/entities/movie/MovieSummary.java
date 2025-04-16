package com.iccues.movie.backend.entities.movie;

import com.iccues.movie.backend.utils.data.DataEntity;
import com.iccues.movie.backend.utils.data.Table;

@DataEntity
@Table("movies")
public class MovieSummary {
    private Long mid;
    private String title;

    public String getTitle() {
        return title;
    }
}
