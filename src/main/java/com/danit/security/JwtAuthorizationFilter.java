package com.danit.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.danit.ApplicationProperties;
import com.danit.exceptions.InvalidJwtTokenException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private UserDetailsService userDetailsService;

  private ApplicationProperties applicationProperties;

  public JwtAuthorizationFilter(AuthenticationManager authManager, UserDetailsService userDetailsService,
                                ApplicationProperties applicationProperties) {
    super(authManager);
    this.userDetailsService = userDetailsService;
    this.applicationProperties = applicationProperties;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest req,
                                  HttpServletResponse res,
                                  FilterChain chain) throws IOException, ServletException {
    String header = req.getHeader(applicationProperties.getAuthHeaderName());

    if (header == null || !header.startsWith(applicationProperties.getAuthTokenPrefix())) {
      chain.doFilter(req, res);
      return;
    }

    UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(req, res);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(applicationProperties.getAuthHeaderName());
    if (token != null) {
      DecodedJWT decodedJwt;
      try {
        decodedJwt = JWT.require(Algorithm.HMAC512(applicationProperties.getSecretKey().getBytes()))
            .build()
            .verify(token.replace(applicationProperties.getAuthTokenPrefix(), ""));
      } catch (JWTVerificationException e) {
        throw new InvalidJwtTokenException("jwt token is invalid");
      }
      String user = decodedJwt.getSubject();
      UserDetails userDetails = userDetailsService.loadUserByUsername(user);
      Collection<? extends GrantedAuthority> grantedAuthorities = userDetails.getAuthorities();
      if (user != null) {
        return new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
      }
      return null;
    }
    return null;
  }
}
