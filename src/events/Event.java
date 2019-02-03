package events;

import java.util.Date;

public class Event {

	private int id;
	private Date createDate;
	private String message;
	private String metric;

	public Event() {
		super();
	}

	public Event(int id, Date createDate, String message, String metric) {
		super();
		this.id = id;
		this.createDate = createDate;
		this.message = message;
		this.metric = metric;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", createDate=" + createDate + ", message=" + message + ", metric=" + metric + "]";
	}

}
