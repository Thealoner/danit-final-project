package com.danit.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.danit.ApplicationProperties;
import com.danit.exceptions.JwtUserMapException;
import com.danit.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;

  private ApplicationProperties applicationProperties;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                 ApplicationProperties applicationProperties) {
    this.authenticationManager = authenticationManager;
    this.applicationProperties = applicationProperties;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req,
                                              HttpServletResponse res) {
    try {
      User user = new ObjectMapper()
          .readValue(req.getInputStream(), User.class);
      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              user.getUsername(),
              user.getPassword(),
              new ArrayList<>())
      );
    } catch (IOException e) {
      throw new JwtUserMapException("JwtAuthenticationFilter cant map input stream to User object");
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req,
                                          HttpServletResponse res,
                                          FilterChain chain,
                                          Authentication auth) throws IOException, ServletException {

    String token = JWT.create()
        .withSubject(((UserDetails) auth.getPrincipal()).getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() +
            applicationProperties.getAuthTokenExpTime()))
        .sign(Algorithm.HMAC512(applicationProperties.getSecretKey().getBytes()));

    log.info("Successful Authentication of User "
        + ((UserDetails) auth.getPrincipal()).getUsername()
    );
    res.addHeader(applicationProperties.getAuthHeaderName(),
        applicationProperties.getAuthTokenPrefix() + token);
    res.setContentType("application/json");
    res.setCharacterEncoding("UTF-8");
    res.getWriter().write(
        "{\"" + applicationProperties.getAuthHeaderName() + "\":\"" +
            applicationProperties.getAuthTokenPrefix() + token + "\"}"
    );
  }
}
