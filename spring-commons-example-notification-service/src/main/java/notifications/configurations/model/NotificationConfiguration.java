package notifications.configurations.model;

import com.github.damianwajser.parsers.JsonToObjectConverter;

import java.util.Map;

public class NotificationConfiguration {

	private String condition;
	private SenderConfiguration sender;
	private Map<String, String> mapping;
	private JsonToObjectConverter converter;

	public JsonToObjectConverter getConverter() {
		return converter;
	}

	public void setMapping(Map<String, String> mapping) {
		this.mapping = mapping;
	}

	public void setConverter(JsonToObjectConverter converter) {
		this.converter = converter;
	}

	public Map<String, String> getMapping() {
		return mapping;
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
