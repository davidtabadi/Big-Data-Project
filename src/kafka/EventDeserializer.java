package kafka;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import events.Event;

public class EventDeserializer implements Deserializer<Event> {

	// object maper to convert json to object == Deserializer
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
	}

	// serialize method takes bytes[] and Deserialize it to Event object
	@Override
	public Event deserialize(String arg0, byte[] arg1) {
		try {
			return objectMapper.readValue(new String(arg1, "UTF-8"), Event.class);
		} catch (Exception e) {
			System.out.println("Error in Deserializing Event");
			return null;
		}
	}

	@Override
	public void close() {
	}

}
