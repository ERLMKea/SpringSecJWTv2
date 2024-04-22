package com.example.springsecjwtv2.config;

import com.example.springsecjwtv2.filter.RequestValidationBeforeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.ignoringRequestMatchers("/contact","/register"))
                //http.csrf((csrf) -> csrf.disable())
                .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/myAccount").hasRole("ADMIN")
                        .requestMatchers("/myBalance").hasAnyRole("ADMIN","SALES")
                        //.requestMatchers("/myAccount", "/myBalance").authenticated()
                        .requestMatchers("/contact", "/register").permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return http.build();
        // var obj = (SecurityFilterChain)http.build();  debug her for at se indbyggede filtre i Spring Security
        //return (SecurityFilterChain)http.build();
    }



    //public PasswordEncoder passwordEncoder() {
    //    return NoOpPasswordEncoder.getInstance();  ingen encrypt
    //}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
