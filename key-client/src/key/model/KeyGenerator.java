package key.model;

import java.util.Random;

public class KeyGenerator {

	private final static Random RANDOM = new Random();
	private final static String SYMBOLS = "ABCDEFGHJKLMNPQRSTUVWXYZ1234567890";

	private KeyGenerator() {
	}

	public static final String generateKey(int octet) {
		String result = "XXXX";
		for (int i = 0; i < (octet - 1); ++i) {
			result += '-';
			for(int j = 0; j < 4; ++j) {
				result += SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
			}
		}
		return result;
	}

}
