package key.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class SetData {

	private int id;
	private final Date created;
	private final String comment;
	private final Date from;
	private final Date to;
	private final List<KeyData> keys = new ArrayList<KeyData>();

	public SetData(Date created, String comment, Date from, Date to) {
		this(-1, created, comment, from, to);
	}

	public SetData(int id, Date created, String comment, Date from, Date to) {
		this.id = id;
		this.created = created;
		this.comment = comment;
		this.from = from;
		this.to = to;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public String getComment() {
		return comment;
	}

	public Date getFrom() {
		return from;
	}

	public Date getTo() {
		return to;
	}

	public void addKey(String key) {
		keys.add(new KeyData(key));
	}

	public Collection<KeyData> getKeys() {
		return keys;
	}

}
