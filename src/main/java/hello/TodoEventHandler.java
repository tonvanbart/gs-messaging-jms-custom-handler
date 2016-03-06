package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ton on 06/03/16.
 */
public class TodoEventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(TodoEventHandler.class);

    /**
     * Handle incoming {@link TodoEvent} by logging it's information.
     * @param todoEvent
     */
    public void handleTodo(TodoEvent todoEvent) {
        LOG.info("handleTodo({}), {}", todoEvent.getId(), todoEvent.getTodoItem());
    }

}
