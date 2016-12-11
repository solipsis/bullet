package com.mygdx.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.BulletGame;
import com.mygdx.game.components.*;

/**
 * Created by dave on 12/10/2016.
 */
public class RingSpawningSystem extends EntityProcessingSystem {


    protected ComponentMapper<Position> positionMapper;
    protected ComponentMapper<Spawner> spawnerMapper;
    protected ComponentMapper<RingPositions> mRingPositions;

    int test = 0;


    public RingSpawningSystem() {super(Aspect.all(Spawner.class, Position.class, RingPositions.class));}

    @Override
    protected void process(Entity e) {


        Spawner spawner = spawnerMapper.get(e);
        Position position = positionMapper.get(e);
        RingPositions ringPos = mRingPositions.get(e);

        spawner.currTime -= 30;


        if (spawner.currTime < 1) {
            test = (test + 10) % 360;

            spawner.currTime = spawner.time;

            Vector2 start = new Vector2(20,20);
            for (int i = 0; i < ringPos.num; i++) {

                start.rotate(360/ringPos.num);
                int bullet = BulletGame.world.create();
                BulletGame.world.edit(bullet)
                       // .add(new LinearMovement(2, 2))
                        //             .add(new LinearMovement(2,2))
                        .add(new RotatedLinearMovement(test, 1f))
                        .add(new Position(position.x + start.x, position.y + start.y))
                        .add(new CircularMovement())
                        //             .add(new Spawner())
                        //             .add(new Countdown(60))
                  //      .add(new Spawner())
                    //    .add(new RingPositions())
                        .add(new Sprite(30, 30, true));

                BulletGame.debugEntitiesCreated += 1;
            }

        }

    }
}
