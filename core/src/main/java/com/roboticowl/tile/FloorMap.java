package com.roboticowl.tile;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.roboticowl.util.Util;

public class FloorMap {
	private final Tileable[] tileData;
	private final int width;
	private final int height;
	private final Tileable EMPTY_TILE = new NullTile();

	public FloorMap(int width, int height, Tileable[] tileData) {
		this.width = width;
		this.height = height;
		this.tileData = tileData;
	}

	public List<Tileable> getAdjacentTiles(Vector2 position) {
		List<Tileable> list = new ArrayList<Tileable>();

		for (int h = (int) (position.y - 1); h<position.y+2;h++) {
			for (int w = (int) (position.x - 1); w<position.x+2;w++) {
				Tileable tile = getTile(w, h);

				if (!(tile instanceof NullTile)) {
					list.add(tile);
				}
			}
		}

		if (list.size() != 9) {
			throw new RuntimeException("Invalid size returned: " + list.size());
		}

		return list;
	}

	public Tileable getTile(int x, int y) {
		if (x < 0 || x > width-1 || y < 0 || y > height-1) {
			return EMPTY_TILE;
		}

		if (x + width * y > tileData.length) {
			Gdx.app.log("",String.format("Invalid coordinates requested: %d, %d", x, y));
		}

		return tileData[x + width * y];

		// Gdx.app.log(this.getClass().getName(),

	}

	public void setAdjacentExplored(int x, int y) {
		List<Tileable> tiles = getAdjacentTiles(new Vector2(x, y));

		for (Tileable t : tiles) {
			t.setExplored(true);
		}
	}

	public Tileable getAdjacentFacingTile(float x, float y, Util.Direction direction) {
		int tileX = (int)x;
		int tileY = (int)y;
		if (direction == Util.Direction.N) {
			tileY--;
		} else if (direction == Util.Direction.S) {
			tileY++;
		} else if (direction == Util.Direction.W) {
			tileX--;
		} else if (direction == Util.Direction.E) {
			tileX++;
		}

		return getTile(tileX, tileY);
	}

	public Tileable getTile(float x, float y) {
		return  getTile((int)x ,(int)y);
	}

	public Tileable[] getTileData() {
		return tileData;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public float[] getRandomPosition() {
		float[] result = new float[width * height];
		int x = 1;
		int y = 1;
		do {
			x = Util.rand.nextInt(getWidth());
			y = Util.rand.nextInt(getHeight());
		} while (getTile(x, y).isBlocking());

		result[0] = x;
		result[1] = y;

		return result;
	}
}
