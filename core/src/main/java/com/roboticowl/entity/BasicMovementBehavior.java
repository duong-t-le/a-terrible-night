package com.roboticowl.entity;

import com.roboticowl.tile.Level;
import com.roboticowl.util.Util;
import com.roboticowl.util.Util.Direction;

public class BasicMovementBehavior extends MovementBehavior {
	public BasicMovementBehavior(Entity entity, Level level, int movementSpaces) {
		super(entity, level, movementSpaces);
	}

	@Override
	public boolean move() {
		Direction direction = getRandomDirection();
		boolean isMoved = false;
		int x = 0;
		int y = 0;
		if (direction == Util.Direction.N) {
			y = - movementSpaces;
		} else if (direction == Util.Direction.W) {
			x = - movementSpaces;
		} else if (direction == Util.Direction.S) {
			y = movementSpaces;
		} else if (direction == Util.Direction.E) {
			x = movementSpaces;
		}
		isMoved = move(x, y);

		return isMoved;
	}

}
