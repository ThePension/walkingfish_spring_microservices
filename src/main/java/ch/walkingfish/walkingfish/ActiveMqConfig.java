package ch.walkingfish.walkingfish;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActiveMqConfig {
    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String username;

    @Value("${spring.activemq.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        org.apache.activemq.ActiveMQConnectionFactory connectionFactory = new org.apache.activemq.ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(brokerUrl);
        connectionFactory.setUserName(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    @Bean
    public org.springframework.jms.core.JmsTemplate jmsTemplate() {
        org.springframework.jms.core.JmsTemplate template = new org.springframework.jms.core.JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        return template;
    }
}
