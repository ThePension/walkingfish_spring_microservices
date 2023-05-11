package ch.walkingfish.walkingfish;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ch.walkingfish.walkingfish.model.Article;

@Configuration
@EnableJms
public class ActiveMqConfig {
    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String username;

    @Value("${spring.activemq.password}")
    private String password;


    /**
     * Template de gestion des messages
     * @param connectionFactory 
     * @param configurer le configurer
     * @return une instance de JmsListenerContainerFactory
     */
    @Bean
    public JmsListenerContainerFactory<?> jmsFactory(ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        // Use object mapper
        factory.setMessageConverter(messageConverter());

        configurer.configure(factory, connectionFactory);
        return factory;
    }

    /**
     * Composant de gestion de la connection jms
     * 
     * @return une instance de ConnectionFactory
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        org.apache.activemq.ActiveMQConnectionFactory connectionFactory = new org.apache.activemq.ActiveMQConnectionFactory();
        connectionFactory.setTrustAllPackages(true);
        connectionFactory.setBrokerURL(brokerUrl);
        connectionFactory.setUserName(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    /**
     * Composant de gestion des messages jms
     * 
     * @return une instance de MessageConverter
     */
    // @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setObjectMapper(objectMapper());
        // converter.setTypeIdPropertyName("_type");
        return converter;
    }

    /**
     * Instance de mapper pour désérialiser les messages json en Article
     * 
     * @return une instance de objectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return mapper;
    }


    // @Bean
    // public ObjectMapper objectMapper() {
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     objectMapper.registerModule(new JavaTimeModule());
    //     objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    //     SimpleModule module = new SimpleModule();
    //     module.addDeserializer(Article.class, new ArticleDeserializer());
    //     objectMapper.registerModule(module);

    //     return objectMapper;
    // }

    /***
     * Composant de gestion des messages jms
     * 
     * @return une instance de JmsTemplate
     */
    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setMessageConverter(messageConverter());
        template.setPubSubDomain(true);
        template.setDestinationResolver(destinationResolver());
        template.setDeliveryPersistent(true);
        return template;
    }

    /**
     * Composant de gestion des routes jms
     * 
     * @return une instance de DynamicDestinationResolver
     */
    @Bean
    DynamicDestinationResolver destinationResolver() {
        return new DynamicDestinationResolver() {
            @Override
            public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain)
                    throws JMSException {
                if (destinationName.endsWith("-t")) {
                    pubSubDomain = true;
                } else {
                    pubSubDomain = false;
                }
                return super.resolveDestinationName(session, destinationName, pubSubDomain);
            }
        };
    }
}
