package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by ton on 06/03/16.
 */
@Component
public class TodoEventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(TodoEventHandler.class);

    /**
     * Handle incoming {@link TodoEvent} by logging it's information.
     * @param todoEvent
     */
    @JmsListener(destination = "${app.jms.todo-queue}")
    public void handleTodo(TodoEvent todoEvent) {
        LOG.info("handleTodo({}), {}", todoEvent.getId(), todoEvent.getTodoItem());
    }

}
