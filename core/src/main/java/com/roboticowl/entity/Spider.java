package com.roboticowl.entity;

import com.badlogic.gdx.graphics.Color;
import com.roboticowl.tile.Level;

public class Spider extends Monster {

	public Spider(Level level, float x, float y) {
		super(level, x, y);
		maxHp = hp = 60 * 5;
		symbol = 'm';
		color = new Color(Color.GREEN);
		movementBehavior = new BasicMovementBehavior(this, level, MOVEMENT_SPACES);
	}



}
