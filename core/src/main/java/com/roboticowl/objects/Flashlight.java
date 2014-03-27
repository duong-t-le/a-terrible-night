package com.roboticowl.objects;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;

public class Flashlight implements Interactable {
	private enum Settings {OFF, LOW, MEDIUM, HIGH}
	private Map<Settings, Integer> lightIntensity;

	public Settings flashlightState;

	public Flashlight() {
		flashlightState = Settings.LOW;

		initLightIntensity();
	}

	private void initLightIntensity() {
		lightIntensity = new HashMap<Settings, Integer>();
		lightIntensity.put(Settings.OFF, 0);
		lightIntensity.put(Settings.LOW, 2);
		lightIntensity.put(Settings.MEDIUM, 3);
		lightIntensity.put(Settings.HIGH, 5);
	}

	public int getIntensity() {
		return lightIntensity.get(flashlightState);
	}

	@Override
	public void interact() {
		int normalizedIndex = flashlightState.ordinal()+1 & Settings.values().length - 1;
		flashlightState = Settings.values()[normalizedIndex];

		Gdx.app.log(getClass().toString(), flashlightState.toString());
	}

}
