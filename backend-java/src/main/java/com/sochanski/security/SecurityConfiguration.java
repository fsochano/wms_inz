/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sochanski.security;

import com.sochanski.ApiUtils;
import com.sochanski.security.user.AppAuthorityRepository;
import com.sochanski.security.user.AppUser;
import com.sochanski.security.user.AppUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final AppUser SYSTEM = new AppUser(
            User.withUsername("SYSTEM")
                    .password("{noop}no-password")
                    .authorities("ORDERING", "PICKING", "SHIPPING", "SETTINGS")
                    .disabled(true)
                    .accountLocked(true)
                    .credentialsExpired(true)
                    .build());

    static {
        SYSTEM.setPassword(null);
    }

    private final AppUserRepository appUserRepository;
    private final AppAuthorityRepository appAuthorityRepository;

    public SecurityConfiguration(AppUserRepository appUserRepository, AppAuthorityRepository appAuthorityRepository) {
        this.appUserRepository = appUserRepository;
        this.appAuthorityRepository = appAuthorityRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable().and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers( ApiUtils.BASE_API_PATH+"/login").permitAll()
                .antMatchers( ApiUtils.BASE_API_PATH+"/logout").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/actuator/health").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout().logoutUrl(ApiUtils.BASE_API_PATH+"/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> httpServletResponse.setStatus(SC_UNAUTHORIZED))
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        var password = passwordEncoder().encode("password");
        var userDetailsManager = userDetailsManager();

        auth.userDetailsService(userDetailsManager)
                .userDetailsPasswordManager(userDetailsManager)
                .passwordEncoder(passwordEncoder());

        userDetailsManager.createUser(
                User.withUsername("admin")
                        .password(password)
                        .authorities("ORDERING", "PICKING", "SHIPPING", "SETTINGS")
                        .build());
        userDetailsManager.createUser(
                User.withUsername("ordering")
                        .password(password)
                        .authorities("ORDERING")
                        .build());
        userDetailsManager.createUser(
                User.withUsername("picking")
                        .password(password)
                        .authorities("PICKING")
                        .build());
        userDetailsManager.createUser(
                User.withUsername("shipping")
                        .password(password)
                        .authorities("SHIPPING")
                        .build());
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AppUserDetailsManager userDetailsManager() {
        return new AppUserDetailsManager(appUserRepository, appAuthorityRepository, passwordEncoder());
    }

}