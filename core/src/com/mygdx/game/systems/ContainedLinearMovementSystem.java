package com.mygdx.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.components.ContainedLinearMovement;
import com.mygdx.game.components.Position;
import com.mygdx.game.components.ZigZag;

/**
 * Created by dave on 4/10/2017.
 */
public class ContainedLinearMovementSystem extends EntityProcessingSystem {

    protected ComponentMapper<ContainedLinearMovement> mContainedLinearMovement;
    protected ComponentMapper<Position> mPosition;

    public ContainedLinearMovementSystem() {
        super(Aspect.all(ContainedLinearMovement.class, Position.class));
    }

    @Override
    protected void process(Entity e) {

      //  System.out.println("containe");
        Position p = mPosition.get(e);
        ContainedLinearMovement cMovement = mContainedLinearMovement.get(e);
        Rectangle box = cMovement.rect;

        p.x += cMovement.deltaX;
        p.y += cMovement.deltaY;

        System.out.println("P.x: " + p.x + "boxX: " + box.x);
        System.out.println("P.y: " + p.y + "boxY: " + box.y);
        if (p.x > box.x + box.width) {
            p.x = box.x + box.width - 10;
            cMovement.deltaX *= -1;
        }
        if (p.x < box.x) {
            p.x = box.x + 10;
            cMovement.deltaX *= -1;
        }
        if (p.y > box.y + box.height) {
            p.y = box.y + box.height - 10;
            cMovement.deltaY *= -1;
        }
        if (p.y < box.y) {
            p.y = box.y + 10;
            cMovement.deltaY *= -1;
        }
    }
}
