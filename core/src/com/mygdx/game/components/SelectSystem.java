package com.mygdx.game.components;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.BulletGame;

/**
 * Created by dave on 12/28/2016.
 */
public class SelectSystem extends EntityProcessingSystem {

    protected ComponentMapper<SelectBox> mSelectBox;
    protected ComponentMapper<Position> mPosition;

    private ShapeRenderer renderer;

    public SelectSystem() {
        super(Aspect.all(SelectBox.class, Position.class));

        renderer = new ShapeRenderer();
        renderer.setProjectionMatrix(BulletGame.camera.combined);
    }

    @Override
    protected void process(Entity e) {
        Position position = mPosition.get(e);
        SelectBox selectBox = mSelectBox.get(e);

        selectBox.rect.setPosition(position.x, position.y);

        renderer.rect(selectBox.rect.x, selectBox.rect.y, selectBox.rect.width, selectBox.rect.height);

    }

    @Override
    protected void begin() {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(1,0,0,1);
    }

    @Override
    protected  void end() {
        renderer.end();
    }
}
