package com.imooc.example.springtxjms.web;

import com.imooc.example.springtxjms.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerResource {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/message1/listen", method = RequestMethod.POST)
    public void create(@RequestParam String msg) {
        jmsTemplate.convertAndSend("customer:msg1:new", msg);
    }

    @PostMapping("/message1/direct")
    public void handle(@RequestParam String msg) {
        customerService.handle(msg);
    }

    @RequestMapping(value = "/message2/listen", method = RequestMethod.POST)
    public void create2(@RequestParam String msg) {
        jmsTemplate.convertAndSend("customer:msg2:new", msg);
    }

    @PostMapping("/message2/direct")
    public void handle2(@RequestParam String msg) {
        customerService.handleInCode(msg);
    }

    @RequestMapping("/message")
    public String read() {
        jmsTemplate.setReceiveTimeout(2000);
        Object obj = jmsTemplate.receiveAndConvert("customer:msg:reply");
        return String.valueOf(obj);
    }
}
