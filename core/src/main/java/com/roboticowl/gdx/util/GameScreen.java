package com.roboticowl.gdx.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public interface GameScreen extends com.badlogic.gdx.Screen {
	public void update(float delta);

	public abstract void setFont(BitmapFont font);

	public abstract BitmapFont getFont();

	public abstract void setBatch(SpriteBatch batch);

	public abstract SpriteBatch getBatch();

	public abstract void setCamera(OrthographicCamera camera);

	public abstract OrthographicCamera getCamera();
}
