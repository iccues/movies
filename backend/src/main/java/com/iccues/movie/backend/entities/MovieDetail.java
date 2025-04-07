package com.iccues.movie.backend.entities;

import com.iccues.movie.backend.utils.data.DataEntity;
import com.iccues.movie.backend.utils.data.Table;

@DataEntity
@Table("movies")
public class MovieDetail {
    private Long id;
    private String title;
}
