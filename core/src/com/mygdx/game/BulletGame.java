package com.mygdx.game;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.components.*;
import com.mygdx.game.systems.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BulletGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	public static FPSLogger fpsLogger = new FPSLogger();

	public static boolean beat = false; // TODO: actually implement this
	public static int beatCount = 30;

	public static Camera camera;
	public static Vector3 mousePos;
	public static final float WORLD_WIDTH = 1000;
	public static final float WORLD_HEIGHT = 1000;
	public static Music currentSong;

	public static long debugEntitiesCreated = 0;

	public static World world;
	
	@Override
	public void create () {


		/*
		Graphics.Monitor currMonitor = Gdx.graphics.getMonitor();
		Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode(currMonitor);
		if(!Gdx.graphics.setFullscreenMode(displayMode)) {
			System.out.println("full screen failed");
		}
		*/
        mousePos = new Vector3();


		camera = new OrthographicCamera(WORLD_WIDTH, WORLD_WIDTH * (WORLD_HEIGHT / WORLD_WIDTH));
		//camera = new OrthographicCamera(1920, 1080);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		camera.update();

		WorldConfiguration config = new WorldConfiguration()
				.setSystem(new RenderingSystem())
				.setSystem(new CircularMovementSystem())
				.setSystem(new LinearMovementSystem())
				.setSystem(new CountdownSystem())
				.setSystem(new SpawningSystem())
				.setSystem(new DebugRenderingSystem())
				.setSystem(new BeatPlottingSystem())
              //  .setSystem(new RingSpawningSystem())
				.setSystem(new PatternEditingSystem())
				.setSystem(new PatternSpawningSystem())
				.setSystem(new MousePositionSystem())
				.setSystem(new SelectSystem())
				.setSystem(new SpacialHashingSystem())
				.setSystem(new RotatedLinearMovementSystem());
		world = new World(config);

		List<Vector2> editOffsets = new ArrayList<Vector2>();

		AudioInputStream audioInputStream;
		Song song;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(Gdx.files.internal("lucid.wav").file())));
			currentSong = Gdx.audio.newMusic(Gdx.files.internal("lucid.wav"));
			currentSong.play();
//			song = new Song(audioInputStream);
//			System.out.printf(song.graphData.toString());

			int songGraph = world.create();
			world.edit(songGraph)
					.add(new Song(audioInputStream));

		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}



		int spawner = world.create();
		world.edit(spawner)
				.add(new Position(400,400))
				.add(new Sprite(200, 200, false))
			//	.add(new CircularMovement())
				//.add(new LinearMovement())
                .add(new Spawner());

        int circleSpawner = world.create();
        world.edit(circleSpawner)
                .add(new Position(600,400))
                .add(new Sprite(200, 200, false))
                	.add(new CircularMovement())
                //.add(new LinearMovement())
                .add(new Spawner())
				.add(new Pattern())
				.add(new EditBox())
				//.add(new SelectBox(300, 300))
				.add(new Edit())
                .add(new RingPositions());
	}

	@Override
	public void render () {

		fpsLogger.log();

		Gdx.gl.glClearColor(0, 0, 0.2f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		world.setDelta(Gdx.graphics.getDeltaTime());
		//System.out.println(debugEntitiesCreated);
		world.process();
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = WORLD_WIDTH;
		camera.viewportHeight = WORLD_WIDTH * (WORLD_HEIGHT/WORLD_WIDTH);
		//camera.viewportWidth = 1920;
		//camera.viewportHeight = 1080;
		camera.update();
		RenderingSystem.shader.begin();
		RenderingSystem.shader.setUniformf("Resolution", WORLD_WIDTH, WORLD_HEIGHT);
		RenderingSystem.shader.end();
	}
}
