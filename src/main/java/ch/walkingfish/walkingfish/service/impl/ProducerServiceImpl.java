package ch.walkingfish.walkingfish.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import ch.walkingfish.walkingfish.model.Article;
import ch.walkingfish.walkingfish.model.Colori;
import ch.walkingfish.walkingfish.model.Picture;
import ch.walkingfish.walkingfish.service.ProducerService;

@Service
public class ProducerServiceImpl implements ProducerService {
         
    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${spring.activemq.queue-name}")
    private String queueName;

    @Override
    public void send(String message) {
        jmsTemplate.convertAndSend(queueName, message);
    }

    @Override
    public void send(Article article) {
        jmsTemplate.convertAndSend(queueName, article);
    }

    @Override
    public void send(Picture picture) {
        jmsTemplate.convertAndSend(queueName, picture);
    }

    @Override
    public void send(List<Article> articles) {
        jmsTemplate.convertAndSend(queueName, articles);
    }

    @Override
    public void send(Colori colori) {
        jmsTemplate.convertAndSend(queueName, colori);
    }
}
