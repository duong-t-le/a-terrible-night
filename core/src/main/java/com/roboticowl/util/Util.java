package com.roboticowl.util;

import java.util.Random;

public class Util {
	public static Random rand = null;

	public enum Direction {
		N, S, E, W
	};

	public static Random getRandUtil() {
		return rand;
	}

	public static Random getRandUtil(long nanoseconds) {
		if (rand == null) {
			if (rand == null) {
				rand = new Random(nanoseconds);
			}
		}

		return rand;
	}

	public static float[] getScreenCoordinate(float x, float y) {
		float[] result = new float[2];
		result[0] = x+y;
		result[1] = x-y;
		return result;
	}
}
