package com.mygdx.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.components.Position;
import com.mygdx.game.components.RotatedLinearMovement;

/**
 * Created by dave on 12/10/2016.
 */
public class RotatedLinearMovementSystem extends EntityProcessingSystem {

    protected ComponentMapper<RotatedLinearMovement> mRotatedLinearMovement;
    protected ComponentMapper<Position> mPosition;



    public RotatedLinearMovementSystem() {super(Aspect.all(RotatedLinearMovement.class, Position.class));}

    @Override
    protected void process(Entity e) {
        RotatedLinearMovement movement = mRotatedLinearMovement.get(e);
        Position position = mPosition.get(e);
        position.x += movement.move.x;
        position.y += movement.move.y;
    }
}
