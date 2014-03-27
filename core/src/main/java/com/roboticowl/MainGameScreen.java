package com.roboticowl;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.roboticowl.entity.Entity;
import com.roboticowl.entity.Monster;
import com.roboticowl.entity.Player;
import com.roboticowl.gdx.util.GameScreen;
import com.roboticowl.tile.Level;
import com.roboticowl.tile.Tile;
import com.roboticowl.tile.Tileable;
import com.roboticowl.util.MessageService;
import com.roboticowl.util.Util;

public class MainGameScreen implements GameScreen {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private final SpriteBatch uiBatch;
	private BitmapFont font;
	private final BitmapFont uiFont;
	public static MessageService messageService;
	private final ShapeRenderer shapeRenderer;

	private final Level officeLevel;
	private final Player player;

	private int screenWidth;
	private int screenHeight;


	public MainGameScreen() {
		officeLevel = new Level(Tile.load());

		uiBatch = new SpriteBatch();

		player = new Player(officeLevel);

		screenWidth = Gdx.app.getGraphics().getWidth();
		screenHeight = Gdx.app.getGraphics().getHeight();

		uiFont = new BitmapFont(Gdx.files.internal("data/press8.fnt"));
		shapeRenderer = new ShapeRenderer();
		messageService = new MessageService(uiFont, uiBatch);

		//Gdx.input.setInputProcessor(new DelayedInputProcessor());
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		shapeRenderer.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.setProjectionMatrix(camera.combined);
		Tileable tile = null;

		float[] position;

		for (int w = officeLevel.map.getWidth(); w > -1 ; w--) {
			for (int h = officeLevel.map.getHeight(); h > -1 ; h--) {
				tile = officeLevel.map.getTile(w, h);
				if (tile.isExplored() || Game.IS_DEBUG) {
					position = Util.getScreenCoordinate(w, h);
					tile.draw(batch, font, position[0] * (Tile.TILE_WIDTH / 2),
							position[1] * (Tile.TILE_HEIGHT / 2), player
							.getFovComponent().isInView(w, h));
				}
			}
		}

		position = Util.getScreenCoordinate(player.getX(), player.getY());
		font.setColor(Color.WHITE);

		player.draw(batch, font, position[0] * (Tile.TILE_WIDTH / 2),
				position[1] * (Tile.TILE_HEIGHT / 2));

		Iterator<Monster> monsterIterator = officeLevel.monsters.iterator();
		while (monsterIterator.hasNext()) {
			Monster m = monsterIterator.next();
			boolean isInView = player.isInView(m.getX(), m.getY());
			if (isInView || Game.IS_DEBUG) {
				float[] mPosition = Util
						.getScreenCoordinate(m.getX(), m.getY());
				if (isInView) {
					m.takeDamage(1);
				}
				m.draw(shapeRenderer, batch, font,
						mPosition[0] * (Tile.TILE_WIDTH / 2),
						mPosition[1] * (Tile.TILE_HEIGHT / 2));

				if (m.isReadyCleanUp()) {
					monsterIterator.remove();
				}
			}

		}

		batch.end();


		Iterator<Monster> monsterIterator1 = officeLevel.monsters.iterator();
		while (monsterIterator1.hasNext()) {
			Monster m = monsterIterator1.next();
			if (player.isInView(m.getX(), m.getY()) || Game.IS_DEBUG) {
				float[] mPosition =  Util.getScreenCoordinate(m.getX(), m.getY());

				m.drawUI(shapeRenderer, mPosition[0] * (Tile.TILE_WIDTH / 2),
						mPosition[1] * (Tile.TILE_HEIGHT / 2));
			}
		}
		drawUI();
	}

	private void drawUI() {
		uiBatch.begin();
		uiFont.setColor(Color.WHITE);
		uiFont.setScale(2f);
		uiFont.draw(uiBatch,
				String.valueOf(Gdx.graphics.getFramesPerSecond() + "fps"), 0,
				screenHeight);

		if (player != null) {
			String facing = player.getFacing().toString();
			uiFont.draw(uiBatch, facing, screenWidth
					- uiFont.getBounds(facing).width, screenHeight);
		}

		messageService.render();

		uiBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.log("", String.format("[%d,%d]", width, height));
		screenWidth = width;
		screenHeight = height;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float delta) {
		int V_CAMERA_MOVEMENT = 6;
		int H_CAMERA_MOVEMENT = 3;
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				camera.translate(-V_CAMERA_MOVEMENT, H_CAMERA_MOVEMENT);
			} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				camera.translate(V_CAMERA_MOVEMENT, H_CAMERA_MOVEMENT);
			} else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				camera.translate(-V_CAMERA_MOVEMENT, -H_CAMERA_MOVEMENT);
			} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				camera.translate(V_CAMERA_MOVEMENT, -H_CAMERA_MOVEMENT);
			}
		} else {
			player.update(delta);
		}

		if (Gdx.input.isKeyPressed(Input.Keys.F5)) {
			Game.IS_DEBUG = !Game.IS_DEBUG;
			Gdx.app.log("", String.valueOf(Game.IS_DEBUG));
		}

		for (Monster m : officeLevel.monsters) {
			m.update(delta);
		}

		focusCamera(player);
		camera.update();
		camera.apply(Gdx.gl10);

		messageService.update(delta);
	}

	public void moveCamera(Util.Direction direction) {
		float[] position = Util.getScreenCoordinate(player.getX(),
				player.getY());
		Gdx.app.log("", player.toString());
		camera.position.set(position[0] * (Tile.TILE_WIDTH / 2),
				position[1] * (Tile.TILE_HEIGHT / 2), 0);

	}

	public void focusCamera(Entity e) {
		float[] position = Util.getScreenCoordinate(player.getX(),
				player.getY());
		camera.position.set(position[0] * (Tile.TILE_WIDTH / 2), position[1]
				* (Tile.TILE_HEIGHT / 2), 0);
	}

	@Override
	public OrthographicCamera getCamera() {
		return camera;
	}

	@Override
	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

	@Override
	public SpriteBatch getBatch() {
		return batch;
	}

	@Override
	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	@Override
	public BitmapFont getFont() {
		return font;
	}

	@Override
	public void setFont(BitmapFont font) {
		this.font = font;
	}

	public Level getOfficeLevel() {
		return officeLevel;
	}

}
