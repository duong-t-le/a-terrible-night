package com.roboticowl.objects;

import com.roboticowl.MainGameScreen;
import com.roboticowl.tile.Tileable;

public class Door implements Interactable {
	private boolean isOpen;
	private final Tileable tile;

	public Door(boolean isOpen, Tileable tile) {
		this.isOpen = isOpen;
		this.tile = tile;
	}

	public Door(char symbol, Tileable tile) {
		this(symbol == '+' ? false : true, tile);
	}

	@Override
	public void interact() {
		isOpen = !isOpen;

		tile.setBlocking(isOpen ? false : true);
		tile.setLightBlocking(isOpen ? false : true);

		tile.setSymbol(isOpen ? '`' : '+');
		if (isOpen) {
			MainGameScreen.messageService.display("You opened the door.");
		} else {
			MainGameScreen.messageService.display("You closed the door.");
		}
	}
}
