package com.security.blogs.Payloads;

import com.security.blogs.Security.CustomUserDetailsService;
import com.security.blogs.Util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestTokenHeader = request.getHeader("Authorization");
        System.out.println("requestTokenHeader - " + requestTokenHeader);
        String username = null;
        String jwtToken = null;

        if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")) {

            jwtToken = requestTokenHeader.substring(7);
            System.out.println("JwtToken (JwtAuthenticationFilter) - " + jwtToken);

            try{
                username = this.jwtUtil.extractUsername(jwtToken);
                System.out.println("Username (JwtAuthFilter) - " + username);
            }catch (IllegalArgumentException e) {
                e.printStackTrace();
                System.out.println("Unable to get Jwt Token!!");
            }catch (ExpiredJwtException e) {
                e.printStackTrace();
                System.out.println("Jwt Token has expired!!");
            }catch (MalformedJwtException e) {
                e.printStackTrace();
                System.out.println("Invalid Jwt Exception");
            }

            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                if(this.jwtUtil.validateToken(jwtToken, userDetails)) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                }else {
                    System.out.println("Invalid Jwt Token!!");
                }

            }else {
                System.out.println("Username or Context is null!!");
            }

        }
        else {
            System.out.println("Jwt Token is not begin with \'Bearer\'");
        }

        filterChain.doFilter(request, response);

    }
}

/*@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;

        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

            jwtToken = requestTokenHeader.substring(7);

            try {
                username = this.jwtUtil.extractUsername(jwtToken);
            }catch (IllegalArgumentException e) {
                e.printStackTrace();
                System.out.println("Unable to get the Jwt Token!!");
                throw new IllegalArgumentException("Error");
            }catch (ExpiredJwtException e) {
                e.printStackTrace();
                System.out.println("Jwt Token has expired!!");
            }catch (MalformedJwtException e) {
                e.printStackTrace();
                System.out.println("Invalid Jwt");
            }

            // Security
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }
            else {
                System.out.println("Token is not validated...");
            }

        }
        else {
            System.out.println("Jwt Token is not begin with Bearer!!");
        }

        filterChain.doFilter(request, response);

    }
}*/
