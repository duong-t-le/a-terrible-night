package com.roboticowl.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.roboticowl.tile.Level;


public abstract class Monster extends Entity {
	protected MovementBehavior movementBehavior;
	protected int MOVEMENT_SPACES = 1;
	long lastMoved = System.nanoTime() / (1000*1000);
	long movementDelayMS = 800;
	private boolean isReadyCleanUp = false;

	public Monster(Level level, float x, float y) {
		super(level, x, y);
	}

	public static <T> Monster generate(Class<T> monsterClass, Level level, int x, int y) {
		try {
			Class[] cArg = new Class[3];
			cArg[0] = Level.class;
			cArg[1] = Integer.class;
			cArg[2] = Integer.class;
			return (Monster) monsterClass.getDeclaredConstructor(cArg).newInstance(level, x, y);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(float delta) {
		if (0 < hp && hp < maxHp) {
			//hp++;
		}
		long currentTimeMS = System.nanoTime() / (1000 * 1000);
		if (currentTimeMS - lastMoved < movementDelayMS) {
			return;
		}

		//clean up
		if (hp <= 0 && currentTimeMS - lastMoved > 10000) {
			isReadyCleanUp = true;
		}

		if (hp <= 0) {
			return;
		}


		movementBehavior.move();
		lastMoved = currentTimeMS;
	}

	public void takeDamage(int value) {
		hp = hp - value;

		if (hp <= 0) {
			symbol = '%';
			color = Color.RED;
			hp = 0;
		}
	}

	public boolean isReadyCleanUp() {
		return isReadyCleanUp;
	}

	public void draw(ShapeRenderer shapeRenderer, SpriteBatch batch, BitmapFont font, float x, float y) {
		super.draw(batch, font, x, y);


	}

	public void drawUI(ShapeRenderer shapeRenderer, float x, float y) {
		if (hp <= 0) {
			return;
		}

		final int healthBarHeight = 8;
		final int healthBarWidth = 25;

		int xoffset = 6;
		int yoffset = 8;
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);

		int screenX = (int) (x + xoffset);
		int screenY = (int) (y + yoffset);
		shapeRenderer.rect(screenX, screenY, healthBarWidth, healthBarHeight);

		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.rect(screenX, screenY,
				healthBarWidth * (getHp() / (float) getMaxHp()), healthBarHeight);
		shapeRenderer.end();
	}


}
