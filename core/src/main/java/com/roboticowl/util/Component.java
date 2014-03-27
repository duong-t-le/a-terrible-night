package com.roboticowl.util;

import com.roboticowl.entity.Entity;
import com.roboticowl.entity.Message;

public interface Component {
	public void update(Entity e, Message message);
	public String getName();
}
