package com.mygdx.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BulletGame;
import com.mygdx.game.components.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dave on 12/29/2016.
 */
public class SpacialHashingSystem extends EntityProcessingSystem {

    public static final int bucketSize = 100;
    int rows = (int)(BulletGame.WORLD_WIDTH / bucketSize);
    int cols = (int)(BulletGame.WORLD_HEIGHT / bucketSize);
    public Map<Integer, List<Integer>> buckets = new HashMap<Integer, List<Integer>>(rows * cols);

    BitmapFont font = new BitmapFont();
    SpriteBatch batch;

    ShapeRenderer renderer;

    protected ComponentMapper<Position> mPosition;

    public SpacialHashingSystem() {
        super(Aspect.all(Position.class));


        batch = new SpriteBatch();
        batch.setProjectionMatrix(BulletGame.camera.combined);

        renderer = new ShapeRenderer();
        renderer.setProjectionMatrix(BulletGame.camera.combined);
    }

    @Override
    protected void begin() {
        for (int i = 0; i < rows * cols; i++) {
            buckets.put(i, new ArrayList<Integer>());
        }
        renderer.begin(ShapeRenderer.ShapeType.Line);
        batch.begin();
    }

    @Override
    protected void process(Entity e) {

        Position position = mPosition.get(e);
        float x = position.x;
        float y = position.y;

        // TODO: replace this. ignore anything outside the grid for now
        if (!(x > BulletGame.WORLD_WIDTH || x < 0 || y > BulletGame.WORLD_HEIGHT || y < 0)) {
            int bucket = getBucket(x, y);
            if (bucket > 99 || bucket < 0) {
                System.out.println("panic");
            }
            buckets.get(bucket).add(e.getId());
        }
    }
    @Override
    protected void end() {
        Vector3 mousePos = BulletGame.mousePos;
        font.draw(batch, "Entities in cell: " + buckets.get(getBucket(mousePos.x, mousePos.y)).size(), 10, 700);

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                renderer.rect(i*bucketSize, j * bucketSize, bucketSize, bucketSize);
            }
        }
        batch.end();
        renderer.end();
    }

    public int getBucket(float x, float y) {
        return (int)Math.floor(x / bucketSize) + (int)Math.floor((y / bucketSize)) * cols;
    }
}
