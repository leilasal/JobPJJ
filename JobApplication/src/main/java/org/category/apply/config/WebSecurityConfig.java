package org.category.apply.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.category.apply.model.Role;
import org.category.apply.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import static org.category.apply.util.UserUtil.PASSWORD_ENCODER;

@Configuration
@EnableWebSecurity
@Slf4j
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, UserService userService) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(PASSWORD_ENCODER);
    }

    private static final String[] SWAGGER_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/rest/admin/**").hasRole(Role.ADMIN.name())
                .antMatchers("/rest/profile/register").anonymous()
                .antMatchers("/rest/profile/**").authenticated()
                .antMatchers(HttpMethod.GET, "/rest/jobdescriptions/**").authenticated()
                .antMatchers("/rest/jobdescriptions/**").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/rest/categories/**").authenticated()
                .antMatchers("/rest/categories/**").hasRole(Role.ADMIN.name())
                .antMatchers("/rest/applied/**").authenticated()
                .antMatchers(SWAGGER_WHITELIST).permitAll()
                .and().httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
    }
}
