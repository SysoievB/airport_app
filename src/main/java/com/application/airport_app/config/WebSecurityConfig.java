package com.application.airport_app.config;

import com.application.airport_app.security.jwt.JwtConfigurer;
import com.application.airport_app.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String AUTH_ENDPOINT = "/api/v1/auth/**";
    private static final String USERS_ENDPOINT = "/api/v1/users/**";
    private static final String CUSTOMERS_ENDPOINT = "/api/v1/customers/**";
    private static final String TICKETS_ENDPOINT = "/api/v1/tickets/**";

    @Autowired
    public WebSecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_ENDPOINT).permitAll()
                //-----------------
                .antMatchers(HttpMethod.GET, USERS_ENDPOINT).hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, USERS_ENDPOINT).hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, USERS_ENDPOINT).hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, USERS_ENDPOINT).hasRole("ADMIN")
                //-----------------
                .antMatchers(HttpMethod.GET, CUSTOMERS_ENDPOINT).hasRole("USER")
                .antMatchers(HttpMethod.POST, CUSTOMERS_ENDPOINT).hasRole("MODERATOR")
                .antMatchers(HttpMethod.PUT, CUSTOMERS_ENDPOINT).hasRole("MODERATOR")
                .antMatchers(HttpMethod.DELETE, CUSTOMERS_ENDPOINT).hasRole("MODERATOR")
                //-----------------
                .antMatchers(HttpMethod.GET, TICKETS_ENDPOINT).hasRole("USER")
                .antMatchers(HttpMethod.POST, TICKETS_ENDPOINT).hasRole("MODERATOR")
                .antMatchers(HttpMethod.PUT, TICKETS_ENDPOINT).hasRole("MODERATOR")
                .antMatchers(HttpMethod.DELETE, TICKETS_ENDPOINT).hasRole("MODERATOR")
                //-----------------
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
