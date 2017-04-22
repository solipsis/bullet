package com.mygdx.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.BulletGame;
import com.mygdx.game.components.CenteredBoundingBox;
import com.mygdx.game.components.Position;
import com.mygdx.game.components.Song;

/**
 * Created by dave on 4/10/2017.
 */
public class CenteredBoundingBoxSystem extends EntityProcessingSystem {

    ShapeRenderer renderer;

    protected ComponentMapper<CenteredBoundingBox> mCenteredBoundingBox;
    protected ComponentMapper<Position> mPosition;

    public CenteredBoundingBoxSystem() {
        super(Aspect.all(CenteredBoundingBox.class, Position.class));

        renderer = new ShapeRenderer();
        renderer.setProjectionMatrix(BulletGame.camera.combined);
    }


    @Override
    protected void begin() {
        renderer.begin(ShapeRenderer.ShapeType.Line);
    }

    @Override
    protected void process(Entity e) {

        Position pos = mPosition.get(e);
        CenteredBoundingBox box = mCenteredBoundingBox.get(e);

        box.rect.setX(pos.x - box.rect.getWidth()/2);
        box.rect.setY(pos.y - box.rect.getHeight()/2);


        renderer.circle(pos.x, pos.y, 10);
        renderer.setColor(1,1,0,1);
        renderer.box(box.rect.x, box.rect.y, 0, box.rect.getWidth(), box.rect.getHeight(), 0);
    }

    @Override
    protected void end() {
        renderer.end();
    }
}
