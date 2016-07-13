package com.mygdx.game;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.components.CircularMovement;
import com.mygdx.game.components.LinearMovement;
import com.mygdx.game.components.Position;
import com.mygdx.game.components.Sprite;
import com.mygdx.game.systems.CircularMovementSystem;
import com.mygdx.game.systems.LinearMovementSystem;
import com.mygdx.game.systems.RenderingSystem;

public class BulletGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	FPSLogger fpsLogger = new FPSLogger();

	public static Camera camera;
	final float WORLD_WIDTH = 1000;
	final float WORLD_HEIGHT = 1000;

	World world;
	
	@Override
	public void create () {


		/*
		Graphics.Monitor currMonitor = Gdx.graphics.getMonitor();
		Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode(currMonitor);
		if(!Gdx.graphics.setFullscreenMode(displayMode)) {
			System.out.println("full screen failed");
		}
		*/



		camera = new OrthographicCamera(WORLD_WIDTH, WORLD_WIDTH * (WORLD_HEIGHT / WORLD_WIDTH));
		//camera = new OrthographicCamera(1920, 1080);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();

		WorldConfiguration config = new WorldConfiguration()
				.setSystem(new RenderingSystem())
				.setSystem(new CircularMovementSystem())
				.setSystem(new LinearMovementSystem());
		world = new World(config);

		int spawner = world.create();
		world.edit(spawner)
				.add(new Position(10,10))
				.add(new Sprite(300, 300))
				.add(new CircularMovement())
				.add(new LinearMovement());
	}

	@Override
	public void render () {

		fpsLogger.log();

		world.setDelta(Gdx.graphics.getDeltaTime());
		world.process();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = WORLD_WIDTH;
		camera.viewportHeight = WORLD_WIDTH * (WORLD_HEIGHT/WORLD_WIDTH);
		//camera.viewportWidth = 1920;
		//camera.viewportHeight = 1080;
		camera.update();
	}
}
