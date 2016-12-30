package com.mygdx.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.BulletGame;
import com.mygdx.game.components.Position;
import com.mygdx.game.components.Sprite;
import com.mygdx.game.managers.AssetManager;

/**
 * Created by dave on 12/14/2016.
 */
public class DebugRenderingSystem extends EntityProcessingSystem {

    protected ComponentMapper<Position> positionMapper;
    protected ComponentMapper<Sprite> mSprite;

    private ShapeRenderer renderer;

    @Override
    public void inserted(Entity entity) {

    }

    @Override
    protected void begin() {

        renderer.begin(ShapeRenderer.ShapeType.Line);
    }

    public DebugRenderingSystem() {
        super(Aspect.all(Sprite.class, Position.class));

        renderer = new ShapeRenderer();
        renderer.setProjectionMatrix(BulletGame.camera.combined);
    }

    @Override
    protected void process(Entity e) {

        Sprite sprite = mSprite.get(e);
        Position position = positionMapper.get(e);
        int spriteWidth = sprite.width;
        int spriteHeight = sprite.height;
      //  sprite.sprite.setPosition(position.x - (spriteWidth/2), position.y-(spriteHeight/2));
 //       renderer.rect(position.x - (spriteWidth/2), position.y-(spriteHeight/2), spriteWidth, spriteHeight);
     }

    @Override
    protected void end() {
        renderer.end();
    }
}
