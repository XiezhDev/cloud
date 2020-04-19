package com.huige.cloud;

import com.huige.cloud.test.activemq.queue.producer.QueueProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CloudApplicationTests {

    @Autowired
    private QueueProducer queueProducer;

    @Test
    public void testProduceMsg() {
        queueProducer.produceMsg();
    }

}
