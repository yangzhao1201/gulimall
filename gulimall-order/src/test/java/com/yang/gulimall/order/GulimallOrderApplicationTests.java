package com.yang.gulimall.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class GulimallOrderApplicationTests {

    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void creatExchange() {
        DirectExchange directExchange = new DirectExchange("hallo-java-exchange",true,false);
        amqpAdmin.declareExchange(directExchange);
        log.info("Exchange[{}]创建成功","hallo-java-exchange");
    }

    @Test
    public void creatQueue() {
        Queue queue = new Queue("hallo-java-Queue",true,false,false);
        amqpAdmin.declareQueue(queue);
        log.info("Queue[{}]创建成功","hallo-java-Queue");
    }

    @Test
    public void creatBinding() {
        Binding binding = new Binding(
                "hallo-java-Queue",
                Binding.DestinationType.QUEUE,
                "hallo-java-exchange",
                "hallo.java",
                null);
        amqpAdmin.declareBinding(binding);
        log.info("Binding[{}]创建成功","hallo-java-Binding");
    }

    @Test
    public void sendMessage() {
        rabbitTemplate.convertAndSend("hallo-java-exchange","hallo.java","hello world!");
    }

}
