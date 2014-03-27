package com.roboticowl.util;

import com.roboticowl.entity.Entity;
import com.roboticowl.entity.Message;

public class Destroyable implements Component {
	private static final String name = "DESTROY";
	private final int maxHp;
	private int currentHp;

	public Destroyable(int maxHp) {
		this.maxHp = this.currentHp = maxHp;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void update(Entity e, Message message) {
		//currentHp += message;

	}

}
