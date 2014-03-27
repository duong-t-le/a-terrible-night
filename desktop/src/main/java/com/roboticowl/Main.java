package com.roboticowl;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "A Terrible Night";
		cfg.useGL20 = false;
		cfg.width = 640;
		cfg.height = 480;
		cfg.vSyncEnabled = true;
		//LwjglInput.keyRepeatTime = .1f;

		new LwjglApplication(new Game(), cfg);
	}
}
