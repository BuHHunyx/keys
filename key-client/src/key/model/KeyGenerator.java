package key.model;

import java.util.Random;

public class KeyGenerator {

	private final static Random RANDOM = new Random();
	private final static String SYMBOLS = "1234567890";

	private KeyGenerator() {
	}

	public static final String generateKey(String octetValue, int octet) {
		String result = "";
		for (int i = 0; i < (octet * 4); ++i) {
			if (((i % 4) == 0) && (i > 0)) {
				result += '-';
			}
			result += (i < octetValue.length()) ? octetValue.charAt(i) : getRandomCharacter();
		}
		return result;
	}

	private static final char getRandomCharacter() {
		return SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()));
	}
}
