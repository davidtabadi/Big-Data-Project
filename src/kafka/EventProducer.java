package kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import events.Event;
import events.EventFactory;

// org.apache.kafka 
// Producer is Publisher in the pub-sub model
public class EventProducer {

//	the Topic name as we created on kafka server
	private final static String TOPIC = "EventTopic";
	private final static String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";

	// createProducer method returns KafkaProducer <String, Event>
	private static KafkaProducer<String, Event> createProducer() {
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		props.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaExampleProducer");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, EventSerializer.class.getName());
		props.put("group.id", "g");
		return new KafkaProducer<String, Event>(props);
	}

	// method creates KafkaProducer and sends records to Topic, the record will be
	// the Random Event object created by the EventFactory
	public void init() {
		KafkaProducer<String, Event> eventProducer = createProducer();
		int i = 0;
		while (i < 10) {
			ProducerRecord<String, Event> record = new ProducerRecord<String, Event>(TOPIC, EventFactory.getNewEvent());
			eventProducer.send(record);
			System.out.println("Record Sent " + record.value());
			i++;
		}
	}

}
