package com.mfe.auth.mfespringsecurity.Utility;

import com.mfe.auth.mfespringsecurity.Services.MyUserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailService userDetailService;

    @Autowired
    private JwtUtil jwtUtil;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) {

        try {
            final String authorizationHeader = httpServletRequest.getHeader("Authorization");

            String username = null;
            String jwt = null;

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                username = jwtUtil.extractUsername(jwt);
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                            UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource()
                            .buildDetails(httpServletRequest));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        } catch (ExpiredJwtException expiredJwtException) {
            String isRefreshToken = httpServletRequest.getHeader("isRefreshToken");
            String requestURL = httpServletRequest.getRequestURL().toString();
            if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshToken")) {
                allowForRefreshToken(expiredJwtException, httpServletRequest);
            } else
                httpServletRequest.setAttribute("exception", expiredJwtException);
        }catch(BadCredentialsException ex) {
            httpServletRequest.setAttribute("exception", ex);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                null, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        request.setAttribute("claims", ex.getClaims());

    }
}
