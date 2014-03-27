package com.roboticowl.tile;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.roboticowl.objects.Interactable;

public class NullTile implements Tileable {

	@Override
	public void setAdjacentExplored(FloorMap map) {
	}

	@Override
	public void setExplored(boolean isExplored) {
	}

	@Override
	public boolean isExplored() {
		return false;
	}

	@Override
	public boolean isBlocking() {
		return false;
	}

	@Override
	public char getSymbol() {
		return 0;
	}


	@Override
	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(SpriteBatch batch, BitmapFont font, float x, float y,
			boolean isInView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSymbol(char c) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Interactable> getInteractableItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBlocking(boolean isBlocking) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLightBlocking() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setLightBlocking(boolean isLightBlockings) {
		// TODO Auto-generated method stub
		
	}
}
