package key.model;

import java.util.Date;

public class ExportData {

	private final String md5;
	private final Date from;
	private final Date to;
	private final boolean active;

	public ExportData(String md5, Date from, Date to, boolean active) {
		this.md5 = md5;
		this.from = from;
		this.to = to;
		this.active = active;
	}

	public String getMd5() {
		return md5;
	}

	public Date getFrom() {
		return from;
	}

	public Date getTo() {
		return to;
	}

	public boolean isActive() {
		return active;
	}
}
