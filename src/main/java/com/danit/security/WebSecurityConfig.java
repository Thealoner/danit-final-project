package com.danit.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private UserDetailsService userDetailsService;

  WebSecurityConfig(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable().authorizeRequests()
        .antMatchers(HttpMethod.GET, "/api/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilter(new JwtAuthenticationFilter(authenticationManager()))
        .addFilter(new JwtAuthorizationFilter(authenticationManager()))
        // this disables session creation on Spring Security
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
  }

  @Bean
  public BCryptPasswordEncoder bcryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
    auth.setUserDetailsService(userDetailsService);
    auth.setPasswordEncoder(bcryptPasswordEncoder());
    return auth;
  }
}
