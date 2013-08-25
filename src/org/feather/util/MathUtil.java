package org.feather.util;

public class MathUtil {
	
	private MathUtil() {
		
	}
	
	public static int random(int range) {
		return (int) (Math.random() * (range + 1));
	}
	
	public static int random(int lowerBound, int upperBound) {
		return lowerBound + random(upperBound - lowerBound);
	}

}
