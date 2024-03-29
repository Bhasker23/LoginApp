package com.springSecuirty.service;

import com.springSecuirty.model.Authority;
import com.springSecuirty.model.Customer;
import com.springSecuirty.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomerRepo customerRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Customer> opt = customerRepo.findByEmail(username);

        if (opt.isPresent()) {

            Customer customer = opt.get();

            List<GrantedAuthority> authorities = new ArrayList<>();

            /*  This below used in Authorized based Authentication.

            List<Authority> auths = customer.getAuthorities();

            for (Authority auth : auths) {
                SimpleGrantedAuthority sgA = new SimpleGrantedAuthority(auth.getName());
                authorities.add(sgA);
            }
            */

            SimpleGrantedAuthority sgA = new SimpleGrantedAuthority(customer.getRole());
            authorities.add(sgA);

//           /*  The below line returning the predefined User class*/
            return new User(customer.getEmail(), customer.getPassword(), authorities);


            /*The below line returning the Custom User class
             * return new CustomerUserDetails(customer);
             */
        } else {
            throw new BadCredentialsException("User Details not found with this user name : " + username);
        }
    }
}
