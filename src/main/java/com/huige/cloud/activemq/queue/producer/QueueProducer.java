package com.huige.cloud.activemq.queue.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
public class QueueProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    /**
     * 触发推送
     */
    public void produceMsg() {
        jmsMessagingTemplate.convertAndSend(queue, "huige");
    }

    /**
     * 间隔推送
     */
    @Scheduled(fixedDelay = 3000)
    public void priduceMsgScheduled() {
        jmsMessagingTemplate.convertAndSend(queue, "huige");
    }
}
