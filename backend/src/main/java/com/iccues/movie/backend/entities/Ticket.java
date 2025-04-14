package com.iccues.movie.backend.entities;

import com.iccues.movie.backend.utils.data.DataEntity;
import com.iccues.movie.backend.utils.data.Generated;
import com.iccues.movie.backend.utils.data.Key;
import com.iccues.movie.backend.utils.data.Table;

@DataEntity
@Table("tickets")
public class Ticket {
    @Generated
    @Key
    private Long tid;
    private Long uid;
    private Long sid;

    public Ticket(Long uid, Long sid) {
        this.uid = uid;
        this.sid = sid;
    }
}
