package key.model;

import org.apache.commons.codec.digest.DigestUtils;

public class KeyData {

	private final String key;

	public KeyData(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public String getHash() {
		return DigestUtils.md5Hex(key);
	}
}
