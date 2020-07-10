package com.imooc.example.springtxjms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private static final Logger log = LoggerFactory.getLogger("CustomerService");

    @Autowired
    private JmsTemplate jmsTemplate;

    @JmsListener(destination = "customer:msg1:new")
    public void handle(String msg) {
        log.info("Get msg1:{}", msg);
        String reply = "Reply1-" + msg;
        jmsTemplate.convertAndSend("customer:msg:reply", reply);
        if (msg.contains("error")) {
            simulateError();
        }
    }
    private void simulateError() {
        throw new RuntimeException("some data error");
    }
}
