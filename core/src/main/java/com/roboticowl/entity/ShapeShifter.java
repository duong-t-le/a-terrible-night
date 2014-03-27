package com.roboticowl.entity;

import com.badlogic.gdx.graphics.Color;
import com.roboticowl.tile.Level;
import com.roboticowl.util.Util;
import com.roboticowl.util.Util.Direction;

public class ShapeShifter extends Monster {

	public ShapeShifter(Level level, float x, float y) {
		super(level, x, y);
		maxHp = hp = 60 * 10;
		symbol = '@';
		color = new Color(Color.ORANGE);
		movementDelayMS = 2500;
		movementBehavior = new MovementBehavior(this, level, MOVEMENT_SPACES) {

			@Override
			public boolean move() {
				boolean isMoved = false;

				do {
					int x = 0;
					int y = 0;

					movementSpaces = Util.rand.nextLong() % 2 == 0 ? 1 : 3;

					Direction direction = getRandomDirection();
					if (direction == Util.Direction.N) {
						y = -movementSpaces;
					} else if (direction == Util.Direction.W) {
						x = -movementSpaces;
					} else if (direction == Util.Direction.S) {
						y = movementSpaces;
					} else if (direction == Util.Direction.E) {
						x = movementSpaces;
					}

					isMoved = move(x, y);
				} while (isMoved == false);

				return isMoved;
			}
		};
	}

}
