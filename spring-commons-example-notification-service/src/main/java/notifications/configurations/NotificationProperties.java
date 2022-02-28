package notifications.configurations;

import notifications.configurations.model.NotificationConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties
public class NotificationProperties {

	private Map<String, NotificationConfiguration> notifications;

	public Map<String, NotificationConfiguration> getNotificationConfigurations() {
		return notifications;
	}

	public void setNotifications(Map<String, NotificationConfiguration> notifications) {
		this.notifications = notifications;
	}
}
