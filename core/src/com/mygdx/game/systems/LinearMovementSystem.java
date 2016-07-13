package com.mygdx.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.components.LinearMovement;
import com.mygdx.game.components.Position;
import com.mygdx.game.components.Sprite;

/**
 * Created by dave on 7/12/2016.
 */
public class LinearMovementSystem extends EntityProcessingSystem {

    protected ComponentMapper<Position> mPosition;
    protected ComponentMapper<LinearMovement> mLinearMovement;

    public LinearMovementSystem() {
        super(Aspect.all(Position.class, LinearMovement.class));
    }

    @Override
    protected void process(Entity e) {
        Position p = mPosition.get(e);
        LinearMovement l = mLinearMovement.get(e);
        p.x += l.deltaX;
        p.y += l.deltaY;
    }
}
