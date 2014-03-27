package com.roboticowl.objects;

import com.roboticowl.MainGameScreen;
import com.roboticowl.tile.Tileable;

public class Desk implements Interactable {
	Tileable tile;

	public Desk(Tileable tile) {
		this.tile = tile;
	}

	@Override
	public void interact() {
		MainGameScreen.messageService.display("It's just a desk.");
	}

}
