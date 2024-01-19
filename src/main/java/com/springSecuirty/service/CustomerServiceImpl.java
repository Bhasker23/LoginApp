package com.springSecuirty.service;

import com.springSecuirty.exception.CustomerException;
import com.springSecuirty.model.Authority;
import com.springSecuirty.model.Customer;
import com.springSecuirty.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepo customerRepo;

    @Override
    public Customer registerCustomer(Customer customer) {

        List<Authority> list = customer.getAuthorities();

        for (Authority auth : list) {
            auth.setCustomer(customer);
        }

        return customerRepo.save(customer);
    }

    @Override
    public Customer getCustomerDetailsByEmail(String email) throws CustomerException {
        return customerRepo.findByEmail(email).orElseThrow(() -> new CustomerException("Customer not found with given Email :" + email));
    }

    @Override
    public List<Customer> getAllCustomerDetails() throws CustomerException {
        return Optional.of(customerRepo.findAll()).orElseThrow(() -> new CustomerException("No Customers Find.."));
    }
}
