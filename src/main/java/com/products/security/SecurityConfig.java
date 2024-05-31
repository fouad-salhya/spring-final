package com.products.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

 @Autowired
 CustomUserDetailsService customUserDetailsService;

 @Bean
 public static PasswordEncoder passwordEncoder() {
  return new BCryptPasswordEncoder();
 }
 @Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		return httpSecurity
				.csrf(cf -> cf.disable())
			    .authorizeHttpRequests(req -> req.requestMatchers("/auth/**").permitAll())
			    .authorizeHttpRequests( res -> res.requestMatchers("/css/**", "/js/**", "/lib/**", "/img/**").permitAll())
			    .authorizeHttpRequests(req -> req.requestMatchers("/restoran/**").permitAll())
			    .formLogin(form -> form
                     .loginPage("/auth/login")
                     .loginProcessingUrl("/auth/login")
                     .defaultSuccessUrl("/restoran/home", true)
                     .permitAll()
             )
			    .logout(lg -> lg.logoutUrl("/auth/logout").logoutSuccessUrl("/auth/login").permitAll())
				.build();
				
	}

 @Autowired
 public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
  auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());

 }
}