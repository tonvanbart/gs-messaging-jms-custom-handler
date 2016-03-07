package hello;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Sample app configuration. Coupled with the meta-data processor, this provides
 * auto-completion for your configuration in modern IDEs.
 *
 * @author Stephane Nicoll
 */
@ConfigurationProperties("app")
public class ApplicationProperties {

	private final Jms jms = new Jms();

	public Jms getJms() {
		return jms;
	}

	static class Jms {

		private String todoQueue;

		public String getTodoQueue() {
			return todoQueue;
		}

		public void setTodoQueue(String todoQueue) {
			this.todoQueue = todoQueue;
		}
	}
}
