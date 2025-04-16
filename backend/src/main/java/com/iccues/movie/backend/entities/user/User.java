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
    private Boolean isAdmin = false;

    public User() {}
    public User(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, this.passwordHash);
    }

    public void setPassword(String password) {
        this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public Long getUid() {
        return uid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
