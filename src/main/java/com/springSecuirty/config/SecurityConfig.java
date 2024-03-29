package com.springSecuirty.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain endPointSecure(HttpSecurity http) throws Exception {

        /*This cors configuration is used when you want to interact with front end this configuration is
        *  required also if you are using Spring Security otherwise you can use @CrossOrigin annotation
        *  on each controller which will be interacted with frontend */

        http.cors(cors -> {
            cors.configurationSource(new CorsConfigurationSource() {
                @Override
                public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                    CorsConfiguration cfg = new CorsConfiguration();

                    cfg.setAllowedOriginPatterns(Collections.singletonList("*"));
                    cfg.setAllowedMethods(Collections.singletonList("*"));
                    cfg.setAllowCredentials(true);
                    cfg.setAllowedHeaders(Collections.singletonList("*"));
                    cfg.setExposedHeaders(List.of("Authorization"));
                    return cfg;
                }
            });
        }).authorizeHttpRequests(auth -> {
            auth.requestMatchers(HttpMethod.POST, "/customer").permitAll().requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                    /* ------- -This us used in Authorized based Authentication.----------

                    .requestMatchers(HttpMethod.GET,"/customers").hasAuthority("VIEWALLCUSTOMER")
                    .requestMatchers(HttpMethod.GET, "/customer/**").hasAnyAuthority("VIEWALLCUSTOMER","VIEWCUSTOMER")
                     */.requestMatchers(HttpMethod.GET, "/customers").hasRole("ADMIN").requestMatchers(HttpMethod.GET, "/customer/**").hasAnyRole("ADMIN", "ROLE").anyRequest().authenticated();
        }).csrf(csrf -> csrf.disable()).formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /* -------1 Approach for InMemoryUserDetailsManager------
    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("12345")
                .authorities("admin")
                .build();

        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("12345")
                .authorities("read")
                .build();


        return  new InMemoryUserDetailsManager(admin,user);
    }


     */

    /*--------- 2 Approach for InMemoryUserDetailsManager -------------

    @Bean
    public InMemoryUserDetailsManager userDetails(){

        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
        UserDetails admin = User.withUsername("admin").password("12345").authorities("admin").build();
        UserDetails user = User.withUsername("user").password("12345").authorities("read").build();
        userDetailsManager.createUser(admin);
        userDetailsManager.createUser(user);

        return userDetailsManager;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

     */

}
