package com.springSecuirty.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain endPointSecure(HttpSecurity hs) throws Exception {

        hs.authorizeHttpRequests(auth -> {
                    auth.requestMatchers(HttpMethod.POST, "/customer").permitAll()
                            .requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll()
                            .requestMatchers(HttpMethod.GET,"/customers").hasAuthority("VIEWALLCUSTOMER")
                            .requestMatchers(HttpMethod.GET, "/customer/**").hasAnyAuthority("VIEWALLCUSTOMER","VIEWCUSTOMER")
                            .anyRequest().authenticated();
                }).csrf(csrf -> csrf.disable())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return hs.build();
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
