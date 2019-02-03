package mysql;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import events.Event;

public class MongoToMySqlETL {

	private static MongoToMySqlETL instance;

//	private MySqlConnector mySqlConnector;

	private long waitingTime = 5;

	private MongoToMySqlETL() {
		MySqlConnector.getInstance();

	}

	public static MongoToMySqlETL getInstance() {
		if (instance == null) {
			return new MongoToMySqlETL();
		}
		return instance;
	}

	private Runnable copyRunnable = () -> {
		Logger.getGlobal().log(Level.CONFIG, "copy runnable");
		List<Event> events = MySqlConnector.getInstance().selectAllEvents();
		for (Event event : events) {
			MySqlConnector.getInstance().insertItem(event);
		}

	};

	public void run() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 20; i++) {

//				while (true) {
					try {
						copyRunnable.run();
						Thread.sleep(waitingTime * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}
