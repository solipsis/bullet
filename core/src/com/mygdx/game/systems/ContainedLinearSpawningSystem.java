package com.mygdx.game.systems;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.components.ContainedLinearMovement;
import com.mygdx.game.components.Position;

/**
 * Created by dave on 4/10/2017.
 */
public class ContainedLinearSpawningSystem extends EntityProcessingSystem {
    @Override
    protected void process(Entity e) {

    }

    public ContainedLinearSpawningSystem() {
        super(Aspect.all(ContainedLinearMovement.class));
    }
}
