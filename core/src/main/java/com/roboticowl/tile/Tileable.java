package com.roboticowl.tile;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.roboticowl.objects.Interactable;

public interface Tileable {

	public abstract void setAdjacentExplored(FloorMap map);

	public abstract void setExplored(boolean isExplored);

	public abstract boolean isExplored();

	public abstract boolean isBlocking();
	public abstract void setBlocking(boolean isBlocking);

	public abstract boolean isLightBlocking();
	public abstract void setLightBlocking(boolean isLightBlockings);

	public abstract char getSymbol();
	public abstract void setSymbol(char c);

	public abstract void draw(SpriteBatch batch, BitmapFont font, float x,
			float y, boolean isInView);

	public abstract Vector2 getPosition();


	List<Interactable> getInteractableItems();

}