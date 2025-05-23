package com.campus.connect.participant.backend.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import com.campus.connect.participant.backend.security.jwt.AuthTokenFilter;
import com.campus.connect.participant.backend.security.services.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

    public final CustomUserDetailsService UserDetailsService;
    
    public SecurityConfig(CustomUserDetailsService UserDetailsService)
    {
        this.UserDetailsService = UserDetailsService;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(UserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                   .authenticationProvider(authenticationProvider())
                   .build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests( auth -> 
            auth.requestMatchers("api/users/signup").permitAll()
            .requestMatchers("api/users/login").permitAll()
            .requestMatchers("/api/societies/get-featured-societies").permitAll()
            .requestMatchers("/api/societies/get-society-by-id").permitAll()
            .requestMatchers("/api/events/featured-events").permitAll() 
            .requestMatchers("/api/events/getEventBySocietyId").permitAll()
            .requestMatchers("/api/competition/getCompetitionBySocietyId").permitAll()
            .requestMatchers("/api/competition/featuredCompetitions").permitAll()
            .requestMatchers("/api/competition/getDetails").permitAll()
            .requestMatchers("/api/events/getDetails").permitAll()
            .requestMatchers("/api/organizer/featuredOrganizers").permitAll()
            .anyRequest().authenticated()); 

        http.authenticationProvider(authenticationProvider()); 
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }

        @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*"); // Allow all origins
        configuration.addAllowedMethod("*");        // Allow all HTTP methods
        configuration.addAllowedHeader("*");        // Allow all headers
        configuration.setAllowCredentials(true);    // Allow credentials (optional)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
}

