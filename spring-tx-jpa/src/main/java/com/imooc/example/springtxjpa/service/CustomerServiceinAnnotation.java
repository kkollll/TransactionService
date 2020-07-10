package com.imooc.example.springtxjpa.service;

import com.imooc.example.springtxjpa.dao.CustomerRepository;
import com.imooc.example.springtxjpa.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceinAnnotation {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }
}
