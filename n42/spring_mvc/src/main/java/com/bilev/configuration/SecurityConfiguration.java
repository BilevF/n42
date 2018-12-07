package com.bilev.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/account", "/contract", "/removeContractOption", "/addNewOption" ,
                        "/addToBasket", "/removeFromBasket", "/clearBasket", "/submitBasket", "/changeTariff",
                        "/changeContractStatus", "/history", "/addMoney")
                .access("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
                .antMatchers("/newTariff", "/tariff", "/tariffs", "/newOption", "/removeOption",
                        "/changeTariffStatus", "/replaceTariff", "/removeTariff", "/findUser", "/allUser", "/user" ,
                        "/newUser")
                .access("hasRole('ROLE_ADMIN')")

                .and().formLogin()//.
                .loginProcessingUrl("/account") // Submit URL
                .loginPage("/")//
                .successForwardUrl("/account")
                .failureUrl("/?error=true")//
                .usernameParameter("username")//
                .passwordParameter("password")
                // Config for Logout Page
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
                .and().exceptionHandling().accessDeniedPage("/accessDenied");
    }

    @Bean
    public ShaPasswordEncoder passwordEncoder(){
        return new ShaPasswordEncoder();
    }

}