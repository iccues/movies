package com.iccues.movie.backend.entities.movie;

import com.iccues.movie.backend.utils.data.DataEntity;
import com.iccues.movie.backend.utils.data.Generated;
import com.iccues.movie.backend.utils.data.Key;
import com.iccues.movie.backend.utils.data.Table;

@DataEntity
@Table("movies")
public class MovieDetail {
    @Key
    @Generated
    private Long mid;
    private String title;
    private String introduction;
}
