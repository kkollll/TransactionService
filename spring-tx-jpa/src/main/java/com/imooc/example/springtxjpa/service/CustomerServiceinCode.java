package com.imooc.example.springtxjpa.service;

import com.imooc.example.springtxjpa.dao.CustomerRepository;
import com.imooc.example.springtxjpa.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class CustomerServiceinCode {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    public Customer create(Customer customer) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_SERIALIZABLE);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);

        TransactionStatus ts = transactionManager.getTransaction(def);

        try {
            customerRepository.save(customer);
            simulateError();
            transactionManager.commit(ts);
            return customer;
        } catch (Exception e) {
            transactionManager.rollback(ts);
            throw e;
        }
    }

    private void simulateError() {
        throw new RuntimeException("some Data error.");
    }
}
