package com.roboticowl.gdx.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class DelayedInputProcessor implements InputProcessor {

	private final long INPUT_DELAY_MICROSECONDS = 225 * 1000 * 1000;
	private long lastInputTime;


	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (System.nanoTime() - lastInputTime < INPUT_DELAY_MICROSECONDS) {
			return true;
		}
		lastInputTime = System.nanoTime();
		Gdx.app.log(this.getClass().toString(), "READY!");
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
