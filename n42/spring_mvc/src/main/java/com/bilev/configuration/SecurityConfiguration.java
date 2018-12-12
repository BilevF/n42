package com.bilev.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

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
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,
                        "/account", "/contract", "/contract/update", "/contract/basket" ,
                        "/contract/tariff/change", "/contract/history", "/user/update", "/user/money")
                .access("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")

                .antMatchers(HttpMethod.GET,
                        "/contract/new", "/tariff", "/tariff/new", "/tariff/list" ,
                        "/tariff/list/available", "/tariff/option/new", "/tariff/replace", "/user/new", "/user",
                        "/user/list", "/rest/user/find")
                .access("hasRole('ROLE_ADMIN')")

                .antMatchers(HttpMethod.POST,
                        "/rest/contract/basket")
                .access("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")

                .antMatchers(HttpMethod.POST,
                        "/rest/contract", "/rest/tariff", "/rest/tariff/option", "/rest/user")
                .access("hasRole('ROLE_ADMIN')")

                .antMatchers(HttpMethod.PATCH,
                        "/rest/contract", "/rest/contract/tariff", "/rest/contract/block",
                        "/rest/contract/unblock", "/rest/user")
                .access("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")

                .antMatchers(HttpMethod.PATCH,
                        "/rest/tariff/block", "/rest/tariff/unblock", "/rest/tariff/replace")
                .access("hasRole('ROLE_ADMIN')")

                .antMatchers(HttpMethod.PUT,
                        "/rest/contract/basket/option")
                .access("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")

                .antMatchers(HttpMethod.DELETE,
                        "/rest/contract/option", "/rest/contract/basket/option", "/rest/contract/basket")
                .access("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")

                .antMatchers(HttpMethod.DELETE,
                        "/rest/tariff", "/rest/tariff/option")
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