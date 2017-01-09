package com.mygdx.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BulletGame;
import com.mygdx.game.components.Position;

import java.util.*;

/**
 * Created by dave on 12/29/2016.
 */
public class SpacialHashingSystem extends EntityProcessingSystem {

    public static final int bucketSize = 100;
    int rows = (int)(BulletGame.WORLD_WIDTH / bucketSize);
    int cols = (int)(BulletGame.WORLD_HEIGHT / bucketSize);
    public Map<Integer, Set<Integer>> buckets = new HashMap<Integer, Set<Integer>>(rows * cols);

    int mouseBucket = -1;

    BitmapFont font = new BitmapFont();
    SpriteBatch batch;

    ShapeRenderer renderer;

    protected ComponentMapper<Position> mPosition;
    protected ComponentMapper<Hitbox> mHitbox;

    public SpacialHashingSystem() {
        super(Aspect.all(Position.class, Hitbox.class));


        batch = new SpriteBatch();
        batch.setProjectionMatrix(BulletGame.camera.combined);

        renderer = new ShapeRenderer();
        renderer.setProjectionMatrix(BulletGame.camera.combined);
    }

    @Override
    protected void begin() {
        for (int i = 0; i < rows * cols; i++) {
            buckets.put(i, new HashSet<Integer>());
        }
        renderer.begin(ShapeRenderer.ShapeType.Line);
        batch.begin();
    }

    private void addToBucket(int bucket, int id) {
        if (bucket > 99 || bucket < 0) {
            //System.out.println("panic");
        } else {
            buckets.get(bucket).add(id);
        }
    }

    @Override
    protected void process(Entity e) {

        Position position = mPosition.get(e);
        Hitbox hitbox = mHitbox.get(e);
        float x = position.x;
        float y = position.y;

        // TODO: replace this. ignore anything outside the grid for now
        if (!(x > BulletGame.WORLD_WIDTH || x < 0 || y > BulletGame.WORLD_HEIGHT || y < 0)) {

            Rectangle rect = hitbox.rect;
            rect.setPosition(x+hitbox.xOffset,y+hitbox.yOffset);

            // TODO: hacky shit
            x = rect.x;
            y = rect.y;

            //int bucket = getBucket(x, y);

            int topLeft = getBucket(x,y+hitbox.rect.height);
            int topRight = getBucket(x+hitbox.rect.width, y+hitbox.rect.height);
            int bottomLeft = getBucket(x,y);
            int bottomRight = getBucket(x+hitbox.rect.width, y);
            addToBucket(topLeft, e.getId());
            addToBucket(topRight, e.getId());
            addToBucket(bottomLeft, e.getId());
            addToBucket(bottomRight, e.getId());
            //buckets.get(bucket).add(e.getId());




            if (topLeft == mouseBucket || topRight == mouseBucket || bottomLeft == mouseBucket || bottomRight == mouseBucket) {
         //       renderer.setColor(1,0,0,1);
            }
         //   renderer.rect(rect.x, rect.y, hitbox.rect.width, hitbox.rect.height);
        //    renderer.setColor(1,1,1,1);
        }


    }
    @Override
    protected void end() {
        Vector3 mousePos = BulletGame.mousePos;
        font.draw(batch, "Entities in cell: " + buckets.get(getBucket(mousePos.x, mousePos.y)).size(), 10, 700);
        mouseBucket = getBucket(mousePos.x, mousePos.y);

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
