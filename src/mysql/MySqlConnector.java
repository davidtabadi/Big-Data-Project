package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import events.Event;
import mongo.MongoPersister;

//  java.sql
// The Class connects to MySQL DataBase 
public class MySqlConnector {

	private static MySqlConnector instance;

	private MySqlConnector() {
		try {
			// MYSQL JDBC Driver
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static MySqlConnector getInstance() {
		if (instance == null) {
			return new MySqlConnector();
		}
		return instance;
	}

//	reads all documents (objects) from mongoDB
	public List<Event> selectAllEvents() {
		List<Event> events = MongoPersister.getInstance().getAllEvents();
		System.out.println("Events From Mongo: " + events.toString() + "Total recorrds are: " + events.size());
		return events;
	}

//	inserts records read from mongoDB into MySQL ,
	public void insertItem(Event event) {

//		check first if the record already copied from MongoDB
//		select only the records which not copied yet 

//      MongoPersister.getInstance().getAllEvents();
		List<Event> all = selectAllEvents();
		for (Event e : all)
			if (e.getId() == event.getId()) {
				System.out.println("Event already copied from MONGO, skipping...");
//				return;
			} else {

//				if not copied yet, insert values to Events Table in MySql Exercise Data Base
//				MySQL connection URL: "jdbc:mysql://localhost:3306/Exercise", "root","david100"

				String SQL_INSERT = "INSERT INTO EVENTS VALUES (?,?,?,?)";
				try {
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Exercise", "root",
							"david100");
					PreparedStatement ps = con.prepareStatement(SQL_INSERT);
					ps.setInt(1, event.getId());

					java.util.Date createDate = event.getCreateDate();
					long time = createDate.getTime();
					ps.setDate(2, new java.sql.Date(time));

					ps.setString(3, event.getMessage());
					ps.setString(4, event.getMetric());

					ps.execute();
					ps.close();
					System.out.println("Event: " + event.toString() + " Inserted to MYSQL.");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
	}

}
