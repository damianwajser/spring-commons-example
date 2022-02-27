package notifications.senders.impl;

import notifications.model.Notification;
import notifications.senders.Sender;
import org.springframework.stereotype.Service;


@Service
public class EmailSender implements Sender {

	@Override
	public void send(Notification n) {
		System.err.println("send push notifications to: " + n.getUserid() + " with message: " + n);
	}

}
