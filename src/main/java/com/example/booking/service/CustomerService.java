package com.example.booking.service;

import com.example.booking.entity.Customer;
import com.example.booking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Optional<Customer> findCustomerById(String userId) {
        return customerRepository.findById(userId);
    }

    public void saveCustomer(Customer c) {
        customerRepository.save(c);
    }
}
