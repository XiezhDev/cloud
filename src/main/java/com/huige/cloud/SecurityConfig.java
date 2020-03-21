package com.huige.cloud;

import com.huige.cloud.auth.AdminDetailsService;
import com.huige.cloud.auth.UnAuthEntryPoint;
import com.huige.cloud.filter.AuthTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *@Author xiezh
 *@Description Spring Security配置类
 *@Date 2018/4/2 9:38
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
/*@Order(SecurityProperties.)*/
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    @Bean
    public AdminDetailsService userDetailsService() {
        return new AdminDetailsService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder1());

        authenticationProvider.setHideUserNotFoundExceptions(false);
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService());
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers( "/assets/**", "/api/**", "/user/", "/user/login", "/user/register");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(authTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(unAuthEntryPoint1())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/assets/**", "/api/**", "/user/", "/user/login", "/user/register").permitAll()
                .anyRequest().authenticated();

        http.headers().cacheControl();
    }

    @Bean
    public AuthTokenFilter authTokenFilterBean() {
        return new AuthTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder1() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UnAuthEntryPoint unAuthEntryPoint1() {
        return new UnAuthEntryPoint();
    }
}

