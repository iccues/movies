package com.iccues.movie.backend.entities.user;

import com.iccues.movie.backend.utils.data.DataEntity;
import com.iccues.movie.backend.utils.data.Generated;
import com.iccues.movie.backend.utils.data.Key;
import com.iccues.movie.backend.utils.data.Table;
import org.mindrot.jbcrypt.BCrypt;

@DataEntity
@Table("users")
public class User {
    @Key
    @Generated
    private Long uid = null;
    private String username;
    private String passwordHash;

    public User() {}
    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public boolean checkPassword(RowUser rowUser) {
        return BCrypt.checkpw(rowUser.getPassword(), this.passwordHash);
    }

    public Long getUid() {
        return uid;
    }
}
