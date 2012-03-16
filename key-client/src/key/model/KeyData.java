package key.model;

import org.apache.commons.codec.digest.DigestUtils;

public class KeyData {

	private final String key;
	private boolean active;
	private String hash;

	KeyData(String key) {
		this(key, true);
	}

	KeyData(String key, boolean active) {
		this.key = key;
		this.active = active;
	}

	public String getKey() {
		return key;
	}

	public String getHash() {
		if (null == hash) {
			hash = DigestUtils.md5Hex(key);
		}
		return hash;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public int hashCode() {
		return key.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof KeyData) {
			return key.equals(((KeyData)obj).key);
		}
		return super.equals(obj);
	}

}
