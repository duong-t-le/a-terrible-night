package com.roboticowl.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.math.Vector2;
import com.roboticowl.entity.Entity;
import com.roboticowl.entity.Message;
import com.roboticowl.tile.Level;
import com.roboticowl.tile.NullTile;
import com.roboticowl.tile.Tileable;

public class FovComponent implements Component {
	public static final String NAME = "FOV";
	private final int focalLength;
	private final Map<Integer, Set<Integer>> focalPoints;
	private final Entity entity;

	public FovComponent(Entity e, int focalLength) {
		this.focalLength = focalLength;
		this.entity = e;
		this.focalPoints = new HashMap<Integer, Set<Integer>>();
	}

	@Override
	public String getName() {
		return FovComponent.NAME;
	}


	public void calculateFov(Level level) {
		calculateFov(level, focalLength);
	}

	public void calculateFov(Level level, int length) {
		focalPoints.clear();
		float x, y;
		int start = 0;
		int end = 0;

		if (entity.getFacing() == Util.Direction.N) {
			start = 225;
			end = 315;
		} else if (entity.getFacing() == Util.Direction.S) {
			start = 45;
			end = 135;
		} else if (entity.getFacing() == Util.Direction.W) {
			start = 135;
			end = 225;
		} else if (entity.getFacing() == Util.Direction.E) {
			start = 315;
			end = 405;
		}

		add((int)entity.getX(), (int)entity.getY());

		Vector2 position = new Vector2();
		Tileable tile;
		for (int i = start; i < end; i=i+4) {
			x = (float) Math.cos(i * 0.01745f);
			y = (float) Math.sin(i * 0.01745f);
			position.set(entity.getX() + 0.5f, entity.getY() + 0.5f);

			for (int j = 0; j < length; j++) {
				float x1 = position.x + x;
				float y1 = position.y + y;

				tile = level.map.getTile(x1, y1);
				if (tile instanceof NullTile) {
					break;
				}

				int xi = (int)x1;
				int yi = (int)y1;

				add(xi, yi);


				tile.setExplored(true);

				if (tile.isLightBlocking()) {
					break;
				}

				position.set(x1, y1);
			}
		}
	}

	private void add(int x1, int y1) {
		int xi = x1;

		if (!focalPoints.containsKey(xi)) {
			focalPoints.put(xi, new HashSet<Integer>());
		}
		focalPoints.get(xi).add(y1);
	}

	public boolean hasLineOfSight(int targetX, int targetY) {
		float[] sourcePoint = entity.getPosition();



		return false;
	}

	public boolean isInView(int x, int y){
		if (!focalPoints.containsKey(x)) {
			return false;
		}

		return focalPoints.get(x).contains(y);
	}

	public boolean isInView(float x, float y) {
		return isInView((int)x, (int)y);
	}

	@Override
	public String toString() {
		return focalPoints.toString();
	}

	@Override
	public void update(Entity e, Message message) {
		// TODO Auto-generated method stub

	}
}
