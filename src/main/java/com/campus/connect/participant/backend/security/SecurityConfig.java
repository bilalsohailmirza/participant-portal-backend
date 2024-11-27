package com.campus.connect.participant.backend.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import com.campus.connect.participant.backend.security.jwt.AuthTokenFilter;
import com.campus.connect.participant.backend.security.services.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;

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
            .authorizeHttpRequests( auth -> 
            auth.requestMatchers("api/users/signup").permitAll()
            .requestMatchers("api/users/login").permitAll()
            .requestMatchers("/api/societies/get-featured-societies").permitAll()
            .requestMatchers("/api/societies/get-society-by-id").permitAll()
            .requestMatchers("/api/events/featured-events").permitAll() 
            .requestMatchers("/api/events/getEventBySocietyId").permitAll()
            .requestMatchers("/api/competition/getCompetitionBySocietyId").permitAll()
            .anyRequest().authenticated());

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }

    
}

