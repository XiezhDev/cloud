package com.huige.cloud.test.activemq.topic.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class TopicConsumer {

    @JmsListener(destination = "${topicname}")
    public void receive(TextMessage textMessage) throws JMSException {
        System.out.println("收到主题：" + textMessage.getText());
    }
}
