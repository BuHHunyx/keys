package key.model;

import java.util.Random;

public class KeyGenerator {

	private final static Random random = new Random();

	private KeyGenerator() {
	}

	public static final String generateKey(int octet) {
		String result = "XXXX";
		for (int i = 0; i < (octet - 1); ++i) {
			result += '-';
			for(int j = 0; j < 4; ++j) {
				result += random.nextInt(10);
			}
		}
		return result;
	}

}
