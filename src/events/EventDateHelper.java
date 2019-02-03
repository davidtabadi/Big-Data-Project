package events;

import java.util.Date;
import java.util.Random;

// Helper Class which generates Random Dates
public class EventDateHelper {

	// method returns RandomDate
	public static Date getRandomDate() {
		Random rnd;
		Date dt;
		long ms;

		// Get a new random instance, seeded from the clock
		rnd = new Random();

		// Get an Epoch value roughly between 1940 and 2010
		// -946771200000L = January 1, 1940
		// Add up to 70 years to it (using modulus on the next long)
		ms = -946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));

		// Construct a date
		dt = new Date(ms);
		return dt;
	}
}
