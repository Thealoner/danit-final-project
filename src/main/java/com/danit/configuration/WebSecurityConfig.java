package com.danit.configuration;


import com.danit.security.AppAuthenticationEntryPoint;
import com.danit.security.JWTAuthenticationFilter;
import com.danit.security.JWTAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
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


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private AppAuthenticationEntryPoint appAuthenticationEntryPoint;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/api/**").permitAll()
        .antMatchers("/test").hasAnyRole("ADMIN", "USER")
        .and()
        .httpBasic()
        .realmName("MY APP REALM")
        .authenticationEntryPoint(appAuthenticationEntryPoint)
    //.and()
//        /*.addFilterBefore(new JWTAuthenticationFilter(authenticationManager()))
//        .addFilterBefore(new JWTAuthorizationFilter(authenticationManager()))*
    //.sessionManagement()
    //.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    ;
  }


  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
    auth.setUserDetailsService(userDetailsService);
    auth.setPasswordEncoder(bCryptPasswordEncoder());
    System.out.println(auth);
    return auth;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authenticationProvider());
    /*auth.inMemoryAuthentication()
        .withUser("user1").password(bCryptPasswordEncoder().encode("123"))
        .roles("ADMIN");*/
  }
}
