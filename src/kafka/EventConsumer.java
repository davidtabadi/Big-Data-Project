package kafka;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import events.Event;
import mongo.MongoPersister;
import mysql.MySqlConnector;

// org.apache.kafka
// Cunsumer is the Subscriber in pub-sub model
public class EventConsumer {

//	the Topic name as we created on kafka server
	private final static String TOPIC = "EventTopic";
	private final static String BOOTSTRAP_SERVERS = "localhost:9092,localhost:9093,localhost:9094";

	// createConsumer method creates KafkaConsumer <String, Event>
	// and the subscribes to Topic Lists to listen to them, the returned
	// consumer is a consumer subscribed to the Topic List
	private static Consumer<String, Event> createConsumer() {
		final Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		props.put("group.id", "g");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, EventDeserializer.class.getName());

		// Create the consumer using props.
		final Consumer<String, Event> consumer = new KafkaConsumer<>(props);

		// Subscribe to the topic.
		consumer.subscribe(Collections.singletonList(TOPIC));
		return consumer;
	}

	// creates KafkaProducer from KafkaProducer class and init() method sending
	// records to Topic as mentioned in the producer class
	public static void runProducer() {
		EventProducer producer = new EventProducer();
		producer.init();
	}

	// creates KafkaConsumer<String,Event> which subscribes to Topic and reads
	// (polls) all event records from the Topic EventTopic, the method also prints
	// each event record on the screen using toString(), and at the ent of the
	// method saves all the event records in MongoDB
	public static void runConsumer() {
		final Consumer<String, Event> consumer = createConsumer();
		int i = 0;
		while (i < 100) {
			final ConsumerRecords<String, Event> consumerRecords = consumer.poll(100);
			for (ConsumerRecord<String, Event> record : consumerRecords) {
				System.out.println("consumer getting " + record.value().toString());

				// MongoDB
				// save all the Event records polled by the consumer in MongoDB
				MongoPersister mongoPersister = MongoPersister.getInstance();
				mongoPersister.saveEvent(record.value());
			}
			i++;
		}
	}

	// ====Main=========
	// Main class methods
	// we run each main method separately

	public static void main(String[] args) {

//		KafkaProducer and KafkaCunsumer and MongoDB
//		=========================================
//		send records to EventTopic by producer (publisher)
		runProducer();
//		poll records  from topic by consumer (subscriber) 
//		and insert records in MongoDB
		runConsumer();

//		MongoDB and MySQL
//		==================
//		read all records from MongoDB and insert them in MySQL 
		List<Event> events = MySqlConnector.getInstance().selectAllEvents();
		for (Event event : events) {
			MySqlConnector.getInstance().insertItem(event);
		}

//		=== when running all methods together (MongoToMySqlETL with the thread) 
//		this error displaying on the console :
//		"SocketTimeout with opened connection in MongoDB"
//		MongoToMySqlETL.getInstance().run();
	}

}
