package com.iccues.movie.backend.service.userInfo;

import com.iccues.movie.backend.entities.Result;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/user_info/*")
public class UserFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpSession session = req.getSession();
        if (session == null || session.getAttribute("user") == null) {
            res.getWriter().println(Result.Err("User not logged in"));
        } else {
            chain.doFilter(req, res);
        }
    }
}
