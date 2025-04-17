package com.iccues.movie.backend.entities.user;

public class UserInfo {
    private String username;
    private Boolean isAdmin = false;

    public UserInfo(User user) {
        this.username = user.getUsername();
        this.isAdmin = user.isAdmin();
    }
}
