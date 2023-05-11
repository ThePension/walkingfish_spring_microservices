package ch.walkingfish.walkingfish.consumer;

import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.walkingfish.walkingfish.consumer.tools.SimpleLog;

@Component
public class LogConsumer {
    
    @JmsListener(destination = "${spring.activemq.queue-name}")
    public void receive(Message message) {
        System.out.println("Received message: " + message.toString());

        ObjectMapper mapper = new ObjectMapper();

        TextMessage textMessage = (TextMessage)message;

        String text = null;

        try {
            text = textMessage.getText();
        } catch (Exception e) {
            System.out.println("Error while getting text from message: " + e.getMessage());
        }

        SimpleLog simpleLog = null;
        try {
            simpleLog = mapper.readValue(text, SimpleLog.class);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (simpleLog != null) System.out.println("Received message 1: " + simpleLog.toString());
        else System.out.println("Received message 2: " + text);
    }
}
