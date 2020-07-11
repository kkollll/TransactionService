package com.imooc.example.springtxjms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Service
public class CustomerService {

    private static final Logger log = LoggerFactory.getLogger("CustomerService");

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Transactional
    @JmsListener(destination = "customer:msg1:new", containerFactory = "msgFactory")
    public void handle(String msg) {
        log.info("Get msg1:{}", msg);
        String reply = "Reply1-" + msg;
        jmsTemplate.convertAndSend("customer:msg:reply", reply);
        if (msg.contains("error")) {
            simulateError();
        }
    }

    @JmsListener(destination = "customer:msg2:new", containerFactory = "msgFactory")
    public void handleInCode(String msg) {
        log.info("Get msg2:{}", msg);
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            String reply = "Reply2-" + msg;
            jmsTemplate.convertAndSend("customer:msg:reply", reply);
            if (msg.contains("error")) {
                transactionManager.rollback(status);
            } else {
                transactionManager.commit(status);
            }
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }

    }

    private void simulateError() {
        throw new RuntimeException("some data error");
    }
}
