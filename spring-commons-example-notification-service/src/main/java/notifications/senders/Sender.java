package notifications.senders;

import notifications.model.Notification;

public interface Sender{
	void send(Notification n);
}
