package key.model;

import org.apache.commons.codec.digest.DigestUtils;

public class KeyData {

	private final String key;
	private String hash;

	public KeyData(String key) {
		this.key = key;
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
