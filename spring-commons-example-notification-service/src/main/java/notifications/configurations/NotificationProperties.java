package notifications.configurations;

import notifications.model.NotificationConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties
public class NotificationProperties {

	private Map<String, NotificationConfiguration> notifications;

	public Map<String, NotificationConfiguration> getNotifications() {
		return notifications;
	}

	public void setNotifications(Map<String, NotificationConfiguration> notifications) {
		this.notifications = notifications;
	}
}
