package ch.walkingfish.walkingfish.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ch.walkingfish.walkingfish.consumer.tools.SimpleLog;

@Component
public class LogConsumer {
    
    @JmsListener(destination = "${spring.activemq.queue-name}")
    public void receive(Object object) {
        System.out.println("Received message: " + object.toString());
        
        ObjectMapper mapper = new ObjectMapper();

        SimpleLog simpleLog = mapper.convertValue(object, SimpleLog.class);

        System.out.println("Received message: " + simpleLog.toString());
    }
}
