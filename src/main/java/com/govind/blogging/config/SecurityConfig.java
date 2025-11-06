package com.govind.blogging.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.govind.blogging.blog.Security.JwtAuthenticationEntryPoint;
import com.govind.blogging.blog.Security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

	/*
	 * @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http)
	 * throws Exception { http .csrf(csrf -> csrf.disable())
	 * .authorizeHttpRequests(auth -> auth
	 * .requestMatchers("/api/auth/**").permitAll() // Allow login/register
	 * .anyRequest().authenticated() // Others need token ) .exceptionHandling(ex ->
	 * ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
	 * .sessionManagement(sess ->
	 * sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	 * 
	 * http.addFilterBefore(jwtAuthenticationFilter,
	 * UsernamePasswordAuthenticationFilter.class); return http.build(); }
	 */
 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          .csrf(csrf -> csrf.disable())
          .authorizeHttpRequests(auth -> auth
              .requestMatchers("/api/auth/**").permitAll()                 // login
              .requestMatchers(HttpMethod.POST, "/api/users/").permitAll() // registration (with trailing slash)
              //.requestMatchers(HttpMethod.POST, "/api/users").permitAll()  // and without slash (just in case)
              .anyRequest().authenticated()
          )
          .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
          .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

	/*
	 * @Bean public PasswordEncoder passwordEncoder() { return new
	 * BCryptPasswordEncoder(); }
	 */
//    
    @Bean
    public PasswordEncoder passwordEncoder() {
        // TEMPORARY for plain-text passwords in DB
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
