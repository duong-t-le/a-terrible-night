package com.roboticowl.tile;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.roboticowl.Game;
import com.roboticowl.objects.Desk;
import com.roboticowl.objects.Door;
import com.roboticowl.objects.Interactable;

public class Tile implements Tileable {
	public float x;
	public float y;
	private char symbol;
	public TileTypes type;
	private boolean isExplored;
	private boolean isBlocking;
	private boolean isLightBlocking;
	public static final int TILE_HEIGHT = (int) (32 * 1.2);
	public static final int TILE_WIDTH = (int) (64 * 1.2);
	public List<Interactable> interactableItems = new ArrayList<Interactable>();

	public enum TileTypes {
		FLOOR, DOOR, WALL
	};

	public enum TileLightness {
		COLOR_DARK_WALL, COLOR_LIGHT_WALL, COLOR_DARK_GROUND, COLOR_LIGHT_GROUND
	};

	public static final Color COLOR_DARK_WALL = new Color(0 / 255f, 0 / 255f,
			100 / 255f, 1f);
	public static Color COLOR_LIGHT_WALL = new Color(130 / 255f, 110 / 255f,
			50 / 255f, 1f);
	public static Color COLOR_DARK_GROUND = new Color(50 / 255f, 50 / 255f,
			150 / 255f, 1f);
	public static Color COLOR_LIGHT_GROUND = new Color(200 / 255f, 180 / 255f,
			50 / 255f, 1f);
	public static Color COLOR_LIGHT_DESK = new Color(102 / 255f, 51 / 255f,
			0 / 255f, 1f);

	public Tile(char symbol) {
		this.symbol = symbol;
		isExplored = false;
		if (symbol == '#' || symbol == '+') {
			setBlocking(true);
			isLightBlocking = true;
		}

		if (symbol == '-') {
			setBlocking(true);
			isLightBlocking = false;
			interactableItems.add(new Desk(this));
		}

		if (symbol == '+' || symbol == '`') {
			interactableItems.add(new Door(symbol, this));
		}
	}

	public static FloorMap load() {

		FileHandle handle = Gdx.files.internal("data/office.dat");
		String data = handle.readString();
		data = data.replaceAll(" ", "");
		String[] dataMap = data.split("\r\n");
		int width = dataMap[0].length();
		int height = dataMap.length;
		Tileable[] tileData = new Tileable[width * height];

		int counter = 0;
		for (String s : dataMap) {

			for (char c : s.toCharArray()) {
				Tileable tile = new Tile(c);
				tileData[counter] = tile;
				counter++;
			}

		}
		return new FloorMap(width, height, tileData);
	}

	@Override
	public void draw(SpriteBatch batch, BitmapFont font, float x, float y,
			boolean isInView) {

		if (!isExplored && !Game.IS_DEBUG) {
			return;
		}

		Color color = Tile.COLOR_DARK_GROUND;
		if (getSymbol() == '.' || getSymbol() == '`') {
			color = isInView ? Tile.COLOR_LIGHT_GROUND : Tile.COLOR_DARK_GROUND;
		} else if (getSymbol() == '#') {
			color = isInView ? Tile.COLOR_LIGHT_WALL : Tile.COLOR_DARK_WALL;
		} else if (getSymbol() == '-' || getSymbol() == '+') {
			color = isInView ? Tile.COLOR_LIGHT_DESK : Tile.COLOR_DARK_WALL;
		}

		font.setColor(color);
		if (getSymbol() == '.' || getSymbol() == '`') {
			font.setScale(2.0f);
		} else {
			font.setScale(2.5f);
		}
		font.draw(batch, String.valueOf(getSymbol()), x, y);
	}

	@Override
	public char getSymbol() {
		return symbol;
	}

	@Override
	public boolean isBlocking() {
		return isBlocking;
	}

	@Override
	public void setBlocking(boolean isBlocking) {
		this.isBlocking = isBlocking;
	}

	@Override
	public boolean isExplored() {
		return isExplored;
	}

	@Override
	public void setExplored(boolean isExplored) {
		this.isExplored = isExplored;
	}

	@Override
	public Vector2 getPosition() {
		return new Vector2(x, y);
	}

	@Override
	public String toString() {
		return String.format("Tile: %c [%f,%f]", symbol, x, y);
	}

	@Override
	public void setSymbol(char c) {
		symbol = c;

	}

	@Override
	public List<Interactable> getInteractableItems() {
		return interactableItems;
	}

	@Override
	public boolean isLightBlocking() {
		return isLightBlocking;
	}

	@Override
	public void setAdjacentExplored(FloorMap map) {
		// TODO Auto-generated method stub

	}

	public void setLightBlocking(boolean isLightBlocking) {
		this.isLightBlocking = isLightBlocking;
	}
}
