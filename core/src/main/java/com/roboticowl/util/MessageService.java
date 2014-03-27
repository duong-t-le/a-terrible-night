package com.roboticowl.util;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MessageService {
	BitmapFont uiFont;
	SpriteBatch spriteBatch;
	Stack<String> messageQueue = new Stack<String>();
	TextRenderer textRenderer = new TextRenderer();

	public MessageService(BitmapFont font, SpriteBatch batch) {
		this.uiFont = font;
		this.spriteBatch = batch;
	}

	public void display(String message) {
		dismiss();
		messageQueue.add(message);
		textRenderer.setString(message);

	}

	public void update(float delta) {
		textRenderer.update(delta);
	}

	public void render() {
		if (messageQueue.size() == 0) {
			return;
		}
		uiFont.draw(spriteBatch, textRenderer.getString(), 5, uiFont.getBounds(textRenderer.getString()).height + 5);
	}

	public void dismiss() {
		if (messageQueue.size() > 0) {
			messageQueue.pop();
		}
	}

	public class TextRenderer {
		long lastNanoSeconds =0;
		String message;
		int endIndex = 0;
		int characterDisplayRate = 50;
		public TextRenderer() {
		}

		public void setString(String message) {
			this.message = message;
			endIndex = 0;
		}

		public void update(float delta) {
			if (System.nanoTime() - lastNanoSeconds > characterDisplayRate * 1000000 &&
					message != null &&
					endIndex < message.length() - 1) /* ms */ {
				endIndex++;
				lastNanoSeconds = System.nanoTime();
			}
		}

		public String getString() {
			return message.substring(0, endIndex);
		}
	}
}
