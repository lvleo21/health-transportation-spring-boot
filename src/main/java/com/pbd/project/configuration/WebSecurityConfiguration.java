package com.pbd.project.configuration;

import com.pbd.project.service.user.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailService)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String loginPage = "/login";
        String logoutPage = "/logout";

        http.
                authorizeRequests()
//                .antMatchers("/").permitAll()
                .antMatchers(loginPage, "/forgout-password/**").permitAll()
                .antMatchers("/").access("hasAuthority('ADMIN') or hasAuthority('GESTOR') or hasAuthority('OPERADOR')")
                .antMatchers("/travels/**").access("hasAuthority('ADMIN') or hasAuthority('GESTOR') or hasAuthority('OPERADOR')")
                .antMatchers("/passengers/**").access("hasAuthority('ADMIN') or hasAuthority('GESTOR') or hasAuthority('OPERADOR')")
                .antMatchers("/users/**").access("hasAuthority('ADMIN') or hasAuthority('GESTOR')")
                .antMatchers("/passengers/**").access("hasAuthority('ADMIN') or hasAuthority('GESTOR') or hasAuthority('OPERADOR')")
                .antMatchers("/drivers/**").access("hasAuthority('ADMIN') or hasAuthority('GESTOR')")
                .antMatchers("/vehicles/**").access("hasAuthority('ADMIN') or hasAuthority('GESTOR')")
                .antMatchers("/health-center/**").access("hasAuthority('ADMIN')")
                .antMatchers("/prefecture/**").access("hasAuthority('ADMIN')")
//
//                .antMatchers("/**", "/travels/**", "/passengers/**").access("hasAuthority('OPERADOR')")
                .anyRequest()
                .authenticated()
                .and().csrf().disable()
                .formLogin()
                .loginPage(loginPage)
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/")
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(logoutPage))
                .logoutSuccessUrl(loginPage).and().exceptionHandling();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/vendor/**",
                        "/icon/**", "/scss/**");
    }



}
