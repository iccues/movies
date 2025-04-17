package com.iccues.movie.backend.utils;

import com.iccues.movie.backend.entities.user.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserSession {
    public static User getUser(HttpServletRequest req) {
        HttpSession session = req.getSession();
        if (session == null) {
            return null;
        }
        Long uid = (Long) session.getAttribute("uid");
        if (uid == null) {
            return null;
        }
        return DataMapper.selectFirst(User.class, "uid = ?", uid);
    }

    public static void DeleteSession(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        if (session != null) session.invalidate();

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }
}
