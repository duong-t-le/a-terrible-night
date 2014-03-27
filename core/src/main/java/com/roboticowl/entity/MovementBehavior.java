package com.roboticowl.entity;

import com.roboticowl.tile.Level;
import com.roboticowl.tile.NullTile;
import com.roboticowl.tile.Tileable;
import com.roboticowl.util.Util;
import com.roboticowl.util.Util.Direction;

public abstract class MovementBehavior {
	Level level;
	int movementSpaces;
	Entity entity;
	public MovementBehavior(Entity entity, Level level, int movementSpaces) {
		this.level = level;
		this.entity = entity;
		this.movementSpaces = movementSpaces;
	}

	public Direction getRandomDirection() {
		int directionIndex = Util.rand.nextInt(Util.Direction.values().length);
		return Util.Direction.values()[directionIndex];
	}

	public abstract boolean move();

	protected boolean move(int dx, int dy) {
		boolean isMoved = false;

		float newX = entity.x + dx;
		float newY = entity.y + dy;

		Tileable tile = level.map.getTile(newX, newY);
		if (!tile.isBlocking() && !(tile instanceof NullTile)) {
			entity.x = newX;
			entity.y = newY;

			isMoved = true;
		}
		return isMoved;
	}
}
