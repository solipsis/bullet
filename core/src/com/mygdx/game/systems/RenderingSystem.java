package com.mygdx.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.BulletGame;
import com.mygdx.game.components.Position;
import com.mygdx.game.components.Sprite;
import com.mygdx.game.managers.AssetManager;

/**
 * Created by dave on 7/10/2016.
 */
public class RenderingSystem extends EntityProcessingSystem {

    protected ComponentMapper<Sprite> spriteMapper;
    protected ComponentMapper<Position> positionMapper;

    private SpriteBatch batch;
    private AssetManager assetManager = new AssetManager();

    @Override
    public void inserted(Entity entity) {
        Sprite sprite = spriteMapper.get(entity.getId());
        if (sprite.deleteMe) {
            sprite.init(assetManager.getBullet());
        } else {
            sprite.init(assetManager.get());
        }
    }

    @Override
    protected void begin() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
    }

    public RenderingSystem() {
        super(Aspect.all(Sprite.class, Position.class));

        batch = new SpriteBatch();
        batch.setProjectionMatrix(BulletGame.camera.combined);
    }

    @Override
    protected void process(Entity e) {
        Sprite sprite = spriteMapper.get(e);
        Position position = positionMapper.get(e);
        int spriteWidth = sprite.width;
        int spriteHeight = sprite.height;
        sprite.sprite.setPosition(position.x - (spriteWidth/2), position.y-(spriteHeight/2));
        sprite.sprite.draw(batch);
    }

    @Override
    protected void end() {
        batch.end();
    }
}
