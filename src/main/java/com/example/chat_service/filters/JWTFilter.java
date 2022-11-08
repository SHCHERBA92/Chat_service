package com.example.chat_service.filters;

import com.example.chat_service.jwt.JwtUtils;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
import com.example.chat_service.services.AccountService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AccountService accountService;

    public JWTFilter(JwtUtils jwtUtils, AccountService accountService) {
        this.jwtUtils = jwtUtils;
        this.accountService = accountService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var cookies = Arrays.stream(request.getCookies())
                .filter(cookie -> "ACCESS-TOKEN".equals(cookie.getName()))
                .collect(Collectors.toList());

        if (cookies.isEmpty()){
            response.sendError(401, "Token is Empty");
            filterChain.doFilter(request, response);
        }else {
            var token = cookies.get(0).getValue();
            if (token.isBlank()){
                response.sendError(401, "Token is Empty");
                filterChain.doFilter(request, response);
            }else {
                var userName = jwtUtils.getUserName(token);
                var account = accountService.loadUserByUsername(userName);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(account, account.getPassword(), account.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                filterChain.doFilter(request, response);
            }
        }
    }
}
