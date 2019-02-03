package events;

import java.util.Random;

// Singleton class EventFactory
public class EventFactory {

	private static EventFactory instance;

//	private CTOR
	private EventFactory() {

	}

//	getInstance method
	public static EventFactory getInstance() {
		if (instance == null) {
			return new EventFactory();
		}
		return instance;
	}

//	method that generates new Random Event object
	public static Event getNewEvent() {
		Event randomEvent = new Event();
		int randomId = new Random().nextInt(10000);
		randomEvent.setId(randomId);
		randomEvent.setCreateDate(EventDateHelper.getRandomDate());
		randomEvent.setMessage("random message: " + randomId);
		randomEvent.setMetric("random message: " + randomId);
		return randomEvent;
	}
}
