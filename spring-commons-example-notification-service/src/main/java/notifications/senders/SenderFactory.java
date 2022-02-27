package notifications.senders;

import notifications.model.Chanel;
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

	public Sender getNotificationSender(Chanel chanel){
		Sender sender;
		switch (chanel){
			case EMAIL: sender = emailSender; break;
			case PUSH:
			default: sender = pushSender;
		}
		return sender;
	}
}
