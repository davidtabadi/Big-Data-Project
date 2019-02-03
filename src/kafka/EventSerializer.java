package kafka;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import events.Event;

public class EventSerializer implements Serializer<Event> {

	// object maper to convert object to json == Serializer
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	}

	// serialize method takes <string, object> and returns serialized bytes[]
	@Override
	public byte[] serialize(String args0, Event event) {
		try {
			return objectMapper.writeValueAsBytes(event);
		} catch (JsonProcessingException e) {
			System.out.println("Error Serializing Event: " + event);
			return null;
		}
	}

	@Override
	public void close() {
	}

}
