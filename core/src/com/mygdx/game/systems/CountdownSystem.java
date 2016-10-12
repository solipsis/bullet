package com.mygdx.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.BulletGame;
import com.mygdx.game.components.Countdown;

/**
 * Created by dave on 10/12/2016.
 */
public class CountdownSystem extends EntityProcessingSystem {

    protected ComponentMapper<Countdown> mCountdown;


    public CountdownSystem() {
        super(Aspect.all(Countdown.class));
    }


    @Override
    protected void process(Entity e) {
        Countdown countdown = mCountdown.get(e);
        countdown.tRemaining -= 1;
        if (countdown.tRemaining <= 0) {
            BulletGame.world.delete(e.getId());
            BulletGame.debugEntitiesCreated -= 1;
        }
    }
}
