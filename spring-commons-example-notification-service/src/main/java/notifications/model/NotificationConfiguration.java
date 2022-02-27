package notifications.model;

import com.github.damianwajser.parsers.JsonParser;

import java.util.Map;

public class NotificationConfiguration {

	private String condition;
	private SenderConfiguration sender;
	private Map<String,String> mapping;
	private JsonParser parser;

	public JsonParser getParser() {
		return parser;
	}

	public void setParser(JsonParser parser) {
		this.parser = parser;
	}

	public Map<String,String> getMapping() {
		return mapping;
	}

	public void setMapping(Map<String,String> mapping) {
		this.mapping = mapping;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public SenderConfiguration getSender() {
		return sender;
	}

	public void setSender(SenderConfiguration sender) {
		this.sender = sender;
	}
}
