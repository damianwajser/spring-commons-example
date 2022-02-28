package notifications.controller;

import com.github.damianwajser.factories.jsonbased.FactoryCriteriaJsonBased;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import notifications.model.Notification;
import notifications.configurations.model.NotificationConfiguration;
import notifications.senders.SenderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);

	@Autowired
	private SenderFactory senderFactory;

	@Autowired
	private FactoryCriteriaJsonBased<NotificationConfiguration> configurationFinder;


	@KafkaListener(topics = "#{'${subscribe.topics}'.split(',')}")
	public void receive(String message) {
		LOGGER.debug("received: {}", message);
		DocumentContext json = JsonPath.parse(message);
		configurationFinder.find(json).stream()
				.map(n -> this.toNotification(n, json))
				.forEach(this::sendMessage);
	}

	private void sendMessage(Notification n) {
		LOGGER.debug("send notification: {}", n);
		senderFactory.getNotificationSender(n.getSender().getChannel()).send(n);
	}

	private Notification toNotification(NotificationConfiguration c, DocumentContext json) {
		try {
			LOGGER.debug("transform notification: {}", c);
			return c.getConverter().convert(json, Notification.class).attachSender(c.getSender());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
