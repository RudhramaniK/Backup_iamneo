package com.examly.springapp.config;

import com.examly.springapp.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// Add these imports for CORS
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    // --- ADD THIS BEAN FOR CORS ---
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Allow your React frontend origin. Use setAllowedOriginPatterns for flexibility.
        configuration.setAllowedOriginPatterns(List.of("http://localhost:3000")); 
        // Allow these HTTP methods
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        // Allow all headers (especially needed for Authorization header)
        configuration.setAllowedHeaders(List.of("*"));
        // Allow cookies/authentication credentials (JWT is sent in header, but this is good practice)
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Apply this CORS config to all paths
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    // --- END OF CORS BEAN ---
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // The .cors().and() below will now use the bean defined above
        http.cors().and().csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            .antMatchers("/api/auth/**").permitAll()
            .antMatchers("/api/polls").authenticated() // Create poll requires authentication
            .antMatchers("/api/polls/*/votes/**").authenticated() // Vote endpoints require authentication
            .antMatchers("/api/polls/**").permitAll() // Other poll endpoints can be public
            .antMatchers("/api/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated();
        
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}