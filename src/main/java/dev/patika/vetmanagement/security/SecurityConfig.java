package dev.patika.vetmanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails user1 = User.builder()
                .username("john")
                .password("{noop}test123")
                .roles("ADMIN")
                .build();
        UserDetails user2 = User.builder()
                .username("marry")
                .password("{noop}test123")
                .roles("EMPLOYEE","MANAGER")
                .build();
        UserDetails user3 = User.builder()
                .username("susan")
                .password("{noop}test123")
                .roles("EMPLOYEE","MANAGER")
                .build();
        return new InMemoryUserDetailsManager(user1,user2,user3);
    }

    //Restring access based on roles
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configurer->
                configurer
                        .requestMatchers(HttpMethod.GET, "/v1/animals").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.PUT, "/v1/animals/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/v1/animals/**").hasRole("MANAGER")



        );
        // use HTTP Basic authentication
        http.httpBasic(Customizer.withDefaults());
        //Disable Cross Site Request Forgery
        http.csrf(csrf->csrf.disable());
        return http.build();
    }
}
