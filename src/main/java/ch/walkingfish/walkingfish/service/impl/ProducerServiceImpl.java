package ch.walkingfish.walkingfish.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import ch.walkingfish.walkingfish.service.ProducerService;

@Service
public class ProducerServiceImpl implements ProducerService {
         
    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${spring.activemq.queue-name}")
    private String queueName;

    /**
     * Send a message to the queue
     * @param message the message to send
     */
    @Override
    public void send(Object message) {
        jmsTemplate.convertAndSend(queueName, message);
    }
}
