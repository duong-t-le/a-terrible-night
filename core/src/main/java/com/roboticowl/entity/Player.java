package com.roboticowl.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.roboticowl.objects.Flashlight;
import com.roboticowl.objects.Interactable;
import com.roboticowl.tile.Level;
import com.roboticowl.tile.NullTile;
import com.roboticowl.tile.Tileable;
import com.roboticowl.util.FovComponent;
import com.roboticowl.util.Util;
import com.roboticowl.util.Util.Direction;

public class Player extends Entity {
	long lastInputTime = 0;

	int INPUT_DELAY_MICROSECONDS = 225 * 1000 * 1000;

	private final int MOVEMENT_SPACES = 1;

	private final int FOV_LENGTH = 5;

	private final Flashlight flashlight = new Flashlight();

	public Player(Level level) {
		super(level);
		color = Color.WHITE;
		fovComponent = new FovComponent(this, FOV_LENGTH);

		symbol = '@';

		x = 1;
		y = 1;

		lastInputTime = System.nanoTime();

		facing = Util.Direction.N;

		recalculateFov(flashlight.getIntensity());

		level.map.setAdjacentExplored((int) x, (int) y);
	}

	private boolean move(float dx, float dy) {
		boolean isMoved = false;


		float newX = x + dx;
		float newY = y + dy;

		Tileable tile = getLevel().map.getTile(newX, newY);
		if (!tile.isBlocking() && !(tile instanceof NullTile)) {
			x = newX;
			y = newY;

			isMoved = true;

			level.map.setAdjacentExplored((int) x, (int) y);

		}


		return isMoved;
	}

	public void move(Vector2 p) {
		move(p.x, p.y);
	}

	public boolean move(Direction direction) {
		boolean isMoved = false;

		facing = direction;

		if (direction == Util.Direction.N) {
			isMoved = move(0, -MOVEMENT_SPACES);
		} else if (direction == Util.Direction.W) {
			isMoved = move(-MOVEMENT_SPACES, 0);
		} else if (direction == Util.Direction.S) {
			isMoved = move(0, MOVEMENT_SPACES);
		} else if (direction == Util.Direction.E) {
			isMoved = move(MOVEMENT_SPACES, 0);
		}

		return isMoved;
	}

	@Override
	public void update(float delta) {
		boolean isMoved = false;

		if (System.nanoTime() - lastInputTime < INPUT_DELAY_MICROSECONDS) {
			return;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			isMoved = move(Util.Direction.N);
			isMoved = true;
		} else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			isMoved = move(Util.Direction.W);
			isMoved = true;
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			isMoved = move(Util.Direction.S);
			isMoved = true;
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			isMoved = move(Util.Direction.E);
			isMoved = true;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.I)) {
			setFacing(Util.Direction.N);
			isMoved = true;
		} else if (Gdx.input.isKeyPressed(Input.Keys.J)) {
			setFacing(Util.Direction.W);
			isMoved = true;
		} if (Gdx.input.isKeyPressed(Input.Keys.K)) {
			setFacing(Util.Direction.S);
			isMoved = true;
		} else if (Gdx.input.isKeyPressed(Input.Keys.L)) {
			setFacing(Util.Direction.E);
			isMoved = true;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.F)) {
			flashlight.interact();
			isMoved = true;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			Tileable tile = level.map.getAdjacentFacingTile(x, y, getFacing());
			for (Interactable item : tile.getInteractableItems()) {
				item.interact();
				isMoved = true;
			}

		}

		if (isMoved) {
			recalculateFov(flashlight.getIntensity());
			lastInputTime = System.nanoTime();
		}
	}
}
