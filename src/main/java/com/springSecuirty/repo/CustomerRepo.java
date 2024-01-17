package com.springSecuirty.repo;

import com.springSecuirty.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer,Integer> {
    public Optional<Customer> findByEmail(String email);

}