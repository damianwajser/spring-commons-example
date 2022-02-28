package notifications.senders;

import notifications.model.Channel;
import notifications.senders.impl.EmailSender;
import notifications.senders.impl.PushSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SenderFactory {

	@Autowired
	private PushSender pushSender;

	@Autowired
	private EmailSender emailSender;

	public Sender getNotificationSender(Channel channel){
		Sender sender;
		switch (channel){
			case EMAIL: sender = emailSender; break;
			case PUSH:
			default: sender = pushSender;
		}
		return sender;
	}
}
