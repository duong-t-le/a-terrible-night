package com.roboticowl.entity;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.roboticowl.tile.Level;
import com.roboticowl.util.Component;
import com.roboticowl.util.FovComponent;
import com.roboticowl.util.Util;

public abstract class Entity {
	protected long id;
	protected float x;
	protected float y;
	protected char symbol = '?';
	protected Level level;
	protected FovComponent fovComponent;
	protected int maxHp;
	protected int hp;
	protected Util.Direction facing;
	protected Color color;

	private final Map<String, Component> components = new HashMap<String, Component>();

	public Entity(Level level) {
		this.id = System.nanoTime();
		this.level = level;

		fovComponent = new FovComponent(this, 1);
		addComponent(fovComponent);
	}

	public void addComponent(Component c) {
		components.put(c.getName(), c);
	}

	public Entity(Level level, float x, float y) {
		this(level);

		this.x = x;
		this.y = y;
	}

	public boolean isInView(int x, int y) {
		return fovComponent.isInView(x, y);
	}

	public boolean isInView(float x, float y) {
		return isInView((int)x, (int)y);
	}

	public void recalculateFov(int focalLength) {
		fovComponent.calculateFov(level, focalLength);
	}

	public void draw(SpriteBatch batch, BitmapFont font, float x, float y) {
		font.setColor(color);
		font.setScale(2.0f);
		font.draw(batch, String.valueOf(getSymbol()), x, y);
	}

	@Override
	public String toString() {
		float[] screenPosition = Util.getScreenCoordinate(x, y);
		return String.format("%s Position:[%.1f,%.1f]\nScreen Position:[%.1f,%.1f]",this.getClass().getSimpleName(), x, y,
				screenPosition[0] * (64 / 2), screenPosition[1] * (32 / 2));
	}

	public char getSymbol() {
		return symbol;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float[] getPosition() {
		float[] result = new float[2];
		result[0] = x;
		result[1] = y;
		return result;
	}

	public Util.Direction getFacing() {
		return facing;
	}

	public Level getLevel() {
		return level;
	}

	public void setFacing(Util.Direction d) {
		facing = d;
	}

	public FovComponent getFovComponent() {
		return fovComponent;
	}

	public abstract void update(float delta);

	public int getHp() {
		return hp;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void sendMessage(Message message) {
		for (String componentName : components.keySet()) {
			//components.get(componentName).update(message);
		}
	}

	public void render() {
		if (components.containsKey(FovComponent.NAME)) {

		}
	}

	public long getId() {
		return id;
	}
}
