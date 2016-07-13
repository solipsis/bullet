package com.mygdx.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.components.CircularMovement;
import com.mygdx.game.components.Position;

/**
 * Created by dave on 7/12/2016.
 */
public class CircularMovementSystem extends EntityProcessingSystem{

    protected ComponentMapper<Position> mPosition;
    protected ComponentMapper<CircularMovement> mCircularMovement;

    public CircularMovementSystem() {
        super(Aspect.all(CircularMovement.class, Position.class));
    }

    @Override
    protected void process(Entity e) {
        Position position = mPosition.get(e);
        CircularMovement cm = mCircularMovement.get(e);

        cm.angle = ((cm.angle + cm.rate)) % 361f;
        float x = (cm.radius * (float)Math.sin(Math.toRadians(cm.angle)));
        float y = (cm.radius * (float)Math.cos(Math.toRadians(cm.angle)));

        position.x += cm.prev_x - x;
        position.y += cm.prev_y - y;

        cm.prev_x = x;
        cm.prev_y = y;
    }
}
