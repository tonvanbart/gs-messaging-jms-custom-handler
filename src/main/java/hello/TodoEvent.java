package hello;

/**
 * Domain class, describing some "to do" item, and the kind of change that happened to it.
 * Created by ton on 06/03/16.
 */
public class TodoEvent {

    private Integer id;

    private EventType eventType;

    private Todo todoItem;

    /** default constructor needed by message conversion. */
    public TodoEvent() {
    }

    public TodoEvent(Integer id, EventType eventType, Todo todoItem) {
        this.id = id;
        this.eventType = eventType;
        this.todoItem = todoItem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Todo getTodoItem() {
        return todoItem;
    }

    public void setTodoItem(Todo todoItem) {
        this.todoItem = todoItem;
    }

    /**
     * Event types enum.
     */
    public static enum EventType {
        CREATED, UPDATED, DONE;
    }

    /**
     * Example domain class.
     */
    public static class Todo {

        private String text;

        private String project;

        public Todo() {
        }

        public Todo(String text, String project) {
            this.text = text;
            this.project = project;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
        }

        @Override
        public String toString() {
            return "Todo {" +
                    "text='" + text + '\'' +
                    ", project='" + project + '\'' +
                    '}';
        }
    }
}
