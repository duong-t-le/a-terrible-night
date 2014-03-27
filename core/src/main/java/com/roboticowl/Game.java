package com.roboticowl;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.roboticowl.gdx.util.GameScreen;
import com.roboticowl.util.Util;

public class Game implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	private GameScreen screen;
	public static long seed;
	public static boolean IS_DEBUG = false;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		Gdx.app.debug("Data", String.format("Resolution: %.0fx%.0f", w, h));
		camera = new OrthographicCamera(w, h);
		//camera.translate(w/2, h/2);
		camera.update();
		camera.apply(Gdx.gl10);

		font = new BitmapFont(Gdx.files.internal("data/press24.fnt"));

		batch = new SpriteBatch();

		seed = System.nanoTime();

		Util.getRandUtil(seed);
		screen = new MainGameScreen();
		screen.setBatch(batch);
		screen.setFont(font);
		screen.setCamera(camera);

		//Gdx.input.setInputProcessor(new DelayedInputProcessor());

	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		screen.update(deltaTime);
		screen.render(deltaTime);


	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
