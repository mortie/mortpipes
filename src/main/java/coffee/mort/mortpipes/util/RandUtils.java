package coffee.mort.mortpipes.util;

import java.util.Random;

public class RandUtils {
	public static Random rand = new Random();

	public static int randInt(int min, int max) {
		return rand.nextInt((max - min) + 1) + min;
	}
}
