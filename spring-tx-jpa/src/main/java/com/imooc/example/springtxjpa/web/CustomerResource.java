package com.imooc.example.springtxjpa.web;

import com.imooc.example.springtxjpa.dao.CustomerRepository;
import com.imooc.example.springtxjpa.domain.Customer;
import com.imooc.example.springtxjpa.service.CustomerServiceinAnnotation;
import com.imooc.example.springtxjpa.service.CustomerServiceinCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerResource {

    @Autowired
    private CustomerServiceinAnnotation customerServiceinAnnotation;

    @Autowired
    private CustomerServiceinCode customerServiceinCode;

    @Autowired
    private CustomerRepository customerRepository;

    // 测试post
    // curl -X POST -d "{\"username\": \"imooc2\", \"password\": \"123\", \"role\": \"user\"}" -H "Content-Type: application/json" http://localhost:8080/api/customer/code
    @RequestMapping(value = "/code", method = RequestMethod.POST)
    public Customer createIncode(@RequestBody Customer customer) {
        return customerServiceinCode.create(customer);
    }

    @RequestMapping(value = "/annotation", method = RequestMethod.POST)
    public Customer createAnnotation(@RequestBody Customer customer) {
        return customerServiceinAnnotation.create(customer);
    }

    @RequestMapping("")
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }
}
