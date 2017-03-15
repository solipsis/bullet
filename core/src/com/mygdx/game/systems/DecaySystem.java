package com.mygdx.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.BulletGame;
import com.mygdx.game.components.Decay;

/**
 * Created by dave on 1/23/2017.
 */
public class DecaySystem extends EntityProcessingSystem {

    protected ComponentMapper<Decay> mDecay;

    public DecaySystem() {
        super(Aspect.all(Decay.class));
    }

    @Override
    protected void process(Entity e) {
        Decay decay = mDecay.get(e);
        decay.timer--;
        if (decay.timer < 0) {
            world.delete(e.getId());
            BulletGame.debugEntitiesCreated--;
        }



    }
}
