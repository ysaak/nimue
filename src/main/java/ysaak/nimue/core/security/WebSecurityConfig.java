package ysaak.nimue.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ysaak.nimue.core.model.User;
import ysaak.nimue.core.service.UserService;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                // Only "/login" with POST method can be acceded without authentication
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                // We filter the sessions/login requests
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                // And filter other requests to check the presence of JWT in header
                .addFilter(new JWTAuthorizationFilter(authenticationManager()));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(login -> {
            User user = userService.findByLogin(login);
            if (user == null || !user.isActive()) {
                throw new UsernameNotFoundException("User not found or disabled");
            }

            return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPasswordDigest(), Collections.emptyList());
        }).passwordEncoder(userService.getPasswordEncoder());
    }
}
