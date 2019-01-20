package com.danit.security;

import com.danit.ApplicationProperties;
import com.danit.utils.WebSocketUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private ApplicationProperties applicationProperties;

  private UserDetailsService userDetailsService;

  private WebSocketUtils webSocketUtils;

  WebSecurityConfig(ApplicationProperties applicationProperties, UserDetailsService userDetailsService,
                    WebSocketUtils webSocketUtils) {
    this.applicationProperties = applicationProperties;
    this.userDetailsService = userDetailsService;
    this.webSocketUtils = webSocketUtils;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(),
        applicationProperties);
    jwtAuthenticationFilter.setFilterProcessesUrl(applicationProperties.getAuth());
    http
        .cors()
        .and()
        .csrf().disable()
        .headers()
        .frameOptions().disable()
        .and()
        .authorizeRequests()
        .antMatchers(webSocketUtils.getStompEndpoint() + "/**").permitAll()
        .antMatchers("/h2-console/**").permitAll()
        .antMatchers("/users/**").hasAuthority("ADMIN")
        .antMatchers("/password/change").authenticated()
        .antMatchers("/users/password/update").permitAll()
        .antMatchers("/users/password/reset").permitAll()
        .antMatchers("/roles/**").hasAuthority("ADMIN")
        .anyRequest().authenticated()
        .and()
        .addFilterBefore(new JwtAuthorizationFilter(authenticationManager(),
                userDetailsService, applicationProperties),
            BasicAuthenticationFilter.class)
        .addFilterBefore(jwtAuthenticationFilter,
            UsernamePasswordAuthenticationFilter.class)
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
