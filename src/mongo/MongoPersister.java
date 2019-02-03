package mongo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

import events.Event;

// com.mongodb
// The class connects to MongoDB Data Base
public class MongoPersister {

	private static MongoPersister instance;

	private MongoPersister() {
	}

	public static MongoPersister getInstance() {
		if (instance == null) {
			return new MongoPersister();
		}
		return instance;
	}

	// method saves Event objects in MongoDB, our DB name in MongoDB is Exercise,
	// the table (collection) name is TopicEvent, in MongoDB terminal use Exercise
	// and then db.TopicEvent.find() for checking the data inserted
	public void saveEvent(Event event) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		DB database = mongoClient.getDB("Exercise");
		DBCollection collection = database.getCollection("TopicEvent");
		BasicDBObject document = new BasicDBObject();
		document.put("id", event.getId());
		document.put("message", event.getMessage());
		document.put("createDate", event.getCreateDate());
		document.put("metric", event.getMetric());
		WriteResult writeResult = collection.insert(document);
		System.out.println("MONGO DB  ---- " + "event with id = " + event.getId() + " inserted " + writeResult);
		System.out.println("Total records of Events Sent: " + getAllEvents());
	}

	// method shows all objects stored in MongoDB, just like "select
	// * from Events" in MySQL and in MongoDB terminal use Exercise
	// and then db.TopicEvent.find() for getAll data
	public List<Event> getAllEvents() {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		DB database = mongoClient.getDB("Exercise");
		DBCollection collection = database.getCollection("TopicEvent");
		DBCursor cursor = collection.find();

		List<Event> events = new ArrayList<>(cursor.count());
		while (cursor.hasNext()) {
			DBObject dbObject = cursor.next();
			events.add(new Event((Integer) dbObject.get("id"), (new Date()), dbObject.get("message").toString(),
					dbObject.get("metric").toString()));
		}
		System.out.println("getAllEvents returning " + events.size());
		return events;
	}

//	/**** Get database ****/ because getDB method is deprecated
//	// if database doesn't exists, MongoDB will create it for you
//	MongoDatabase mydatabase = mongoClient.getDatabase("mydatabase");
//
//	/**** Get collection / table from 'testdb' ****/
//	// if collection doesn't exists, MongoDB will create it for you
//
//	FindIterable<Document> mydatabaserecords = mydatabase.getCollection("collectionName").find();
//	MongoCursor<Document> iterator = mydatabaserecords.iterator();while(iterator.hasNext())
//	{
//		Document doc = iterator.next();
//		// do something with document
//	}

}
