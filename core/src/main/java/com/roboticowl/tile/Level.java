package com.roboticowl.tile;

import java.util.ArrayList;
import java.util.List;

import com.roboticowl.entity.Monster;
import com.roboticowl.entity.ShapeShifter;
import com.roboticowl.entity.Spider;

public class Level {
	public int width;
	public int height;
	public FloorMap map;
	public List<Monster> monsters;
	public Monster[] monsterList;

	public Level(FloorMap map) {
		this.map = map;
		initMonsters();
	}

	private void initMonsters() {
		monsters = new ArrayList<Monster>();
		monsterList = new Monster[map.getWidth() * map.getHeight()];
		for (int i = 0; i < 25; i++) {
			float[] position = map.getRandomPosition();
			monsters.add(new Spider(this, position[0], position[1]));
		}

		for (int i = 0; i < 10; i++) {
			float[] position = map.getRandomPosition();
			monsters.add(new ShapeShifter(this, position[0], position[1]));
		}
	}

	public void update(Monster m) {
		for (Monster thisM : monsterList) {
			if (thisM == m) {

			}
		}
	}
}
