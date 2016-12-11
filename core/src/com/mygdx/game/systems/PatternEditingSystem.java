package com.mygdx.game.systems;

import com.artemis.Aspect;
import com.artemis.EntitySystem;
import com.mygdx.game.components.Position;

/**
 * Created by dave on 12/11/2016.
 */
public class PatternEditingSystem extends EntitySystem {

    @Override
    protected void processSystem() {

    }

    public PatternEditingSystem() {
        super(Aspect.all(Position.class));
    }

    @Subscribe

}
