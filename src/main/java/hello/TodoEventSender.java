package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * Sends a {@link TodoEvent} as a JSON text message.
 * Created by ton on 06/03/16.
 */
public class TodoEventSender {

    private final JmsTemplate jmsTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(TodoEventSender.class);

    public TodoEventSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        LOG.info("initialized");
    }

    public void sendEvent(TodoEvent event) {
        LOG.info("sendEvent({},{},{})",
                event.getId(), event.getEventType(), event.getTodoItem().getText());
        jmsTemplate.convertAndSend("todo-destination", event);
    }

}
