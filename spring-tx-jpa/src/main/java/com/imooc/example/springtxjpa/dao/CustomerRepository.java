package com.imooc.example.springtxjpa.dao;

import com.imooc.example.springtxjpa.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by mavlarn on 2018/1/20.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findOneByUsername(String username);
}