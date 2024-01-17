package com.springSecuirty.controller;

import com.springSecuirty.model.Customer;
import com.springSecuirty.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/hello")
    public String testHandler() {
        return "Welcome to Spring Security";
    }

    @PostMapping("/customer")
    public ResponseEntity<Customer> saveCustomerHandler(@RequestBody Customer customer) {

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));

        return new ResponseEntity<>(customerService.registerCustomer(customer), HttpStatus.CREATED);
    }

    @GetMapping("/customer/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {

        return new ResponseEntity<>(customerService.getCustomerDetailsByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomerDetails(), HttpStatus.OK);
    }

    @GetMapping("/signIn")
    public ResponseEntity<String> loggedInCustomerHandler(Authentication auth){

        System.out.println(auth);
        Customer customer = customerService.getCustomerDetailsByEmail(auth.getName());

        return  new ResponseEntity<>(customer.getName() + " Logged In successfully ", HttpStatus.OK);
    }


}
