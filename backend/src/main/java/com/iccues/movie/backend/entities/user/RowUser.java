package com.iccues.movie.backend.entities.user;

import org.mindrot.jbcrypt.BCrypt;

public class RowUser {
    private String username;
    private String password;

    public User intoHashedUser() {
        return new User(username, BCrypt.hashpw(password, BCrypt.gensalt()));
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "RowUser [username=" + username + ", password=" + password + "]";
    }
}
