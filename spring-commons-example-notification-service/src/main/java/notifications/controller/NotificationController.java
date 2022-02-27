package notifications.controller;

import com.github.damianwajser.factories.jsonbased.FactoryCriteriaJsonBased;
import notifications.model.Notification;
import notifications.model.NotificationConfiguration;
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

	@KafkaListener(topics = "#{'${test.topic}'.split(',')}")
	public void receive(String json) {
		LOGGER.debug("received: {}", json);
		configurationFinder.find(json).stream()
				.map(n -> this.toNotification(n, json))
				.forEach(this::sendMessage);
	}

	private void sendMessage(Notification n) {
		LOGGER.debug("send notification: {}", n);
		senderFactory.getNotificationSender(n.getSender().getChanel()).send(n);
	}

	private Notification toNotification(NotificationConfiguration c, String json) {
		try {
			LOGGER.debug("transform notification: {}", c);
			return c.getParser().parse(json, Notification.class).attachSender(c.getSender());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
