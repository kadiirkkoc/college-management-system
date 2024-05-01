package system.collegemanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import system.collegemanagement.model.UserRole;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(UserDetailsService userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET , "/faculty" , "/department" , "/lesson", "/instructor" , "/student/").hasAnyAuthority(UserRole.ADMIN.getAuthority() , UserRole.INSTRUCTOR.getAuthority() ,UserRole.STUDENT.getAuthority())
                        .requestMatchers(HttpMethod.POST, "/faculty","/student","/instructor","/department","/lesson").hasAuthority(UserRole.ADMIN.getAuthority())
                        .requestMatchers(HttpMethod.PUT,"/instructor/").hasAnyAuthority(UserRole.ADMIN.getAuthority(),UserRole.INSTRUCTOR.getAuthority())
                        .requestMatchers(HttpMethod.PUT,"/student/").hasAnyAuthority(UserRole.ADMIN.getAuthority(),UserRole.STUDENT.getAuthority())
                        .requestMatchers(HttpMethod.PUT, "/faculty" , "/department").hasAuthority(UserRole.ADMIN.getAuthority())
                        .requestMatchers(HttpMethod.PUT, "/lesson").hasAnyAuthority(UserRole.ADMIN.getAuthority(),UserRole.INSTRUCTOR.getAuthority())
                        .requestMatchers(HttpMethod.DELETE,"/faculty" , "/department" , "/lesson", "/instructor" , "/student/").hasAuthority(UserRole.ADMIN.getAuthority())
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
