package com.springSecuirty.service;

import com.springSecuirty.exception.CustomerException;
import com.springSecuirty.model.Customer;

import java.util.List;

public interface CustomerService {

    public Customer registerCustomer(Customer  customer);

    public Customer getCustomerDetailsByEmail(String email) throws CustomerException;

    public List<Customer> getAllCustomerDetails() throws CustomerException;

}
