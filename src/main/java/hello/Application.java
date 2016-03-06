
package hello;

import java.io.File;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.util.FileSystemUtils;

@SpringBootApplication
@EnableJms
public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    @Bean
    public MessageConverter jsonMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("object-type");
        return converter;
    }

    @Bean
    public JmsTemplate jsonJmsTemplate(MessageConverter jsonMessageConverter, ConnectionFactory connectionFactory) {
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }

    @Bean
    public TodoEventSender eventSender(JmsTemplate jsonJmsTemplate) {
        return new TodoEventSender(jsonJmsTemplate);
    }

    @Bean
    public TodoEventHandler eventHandler() {
        return new TodoEventHandler();
    }

    @Bean
    public MessageListenerAdapter todoEventAdapter(MessageConverter jsonMessageConverter, TodoEventHandler eventHandler) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(eventHandler);
        adapter.setDefaultListenerMethod("handleTodo");
        adapter.setMessageConverter(jsonMessageConverter);
        return adapter;
    }

    @Bean
    DefaultMessageListenerContainer jmsContainer(ConnectionFactory connectionFactory, MessageListenerAdapter todoEventAdapter) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setDestinationName("todo-destination");
        container.setMessageListener(todoEventAdapter);
        return container;
    }

    public static void main(String[] args) {
        // Clean out any ActiveMQ data from a previous run
        FileSystemUtils.deleteRecursively(new File("activemq-data"));

        // Launch the application
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        TodoEventSender sender = context.getBean(TodoEventSender.class);

        LOGGER.info("sending first item");
        TodoEvent.Todo todo = new TodoEvent.Todo("first todo", "project1");
        sender.sendEvent(new TodoEvent(1, TodoEvent.EventType.CREATED, todo));

        LOGGER.info("sending second item");
        TodoEvent.Todo todo2 = new TodoEvent.Todo("second todo", "project1");
        sender.sendEvent(new TodoEvent(2, TodoEvent.EventType.CREATED, todo2));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        context.close();
        FileSystemUtils.deleteRecursively(new File("activemq-data"));
    }

}
