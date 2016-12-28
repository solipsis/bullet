package com.mygdx.game.components;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.BulletGame;

/**
 * Created by dave on 12/13/16.
 */
public class PatternSpawningSystem extends EntityProcessingSystem {

    protected ComponentMapper<Position> positionMapper;
    protected ComponentMapper<Spawner> spawnerMapper;
    protected ComponentMapper<Pattern> patternMapper;

    int test = 0;

    public PatternSpawningSystem() {
        super(Aspect.all(Pattern.class, Spawner.class, Position.class));
    }

    @Override
    protected void process(Entity e) {

        Spawner spawner = spawnerMapper.get(e);
        Position position = positionMapper.get(e);
        Pattern pattern = patternMapper.get(e);


        spawner.currTime -= 60;


        if (spawner.currTime < 1) {
            test = (test + 10) % 360;

            spawner.currTime = spawner.time;
            //System.out.println("offsetSize: " + pattern.offsets.size());

            //Vector2 start = new Vector2(20,20);
            Vector2 start = new Vector2(0, 0);
            for (int i = 0; i < pattern.offsets.size(); i++) {

                // start.rotate(360/ringPos.num);
                int bullet = BulletGame.world.create();
                BulletGame.world.edit(bullet)
                        // .add(new LinearMovement(2, 2))
                        //             .add(new LinearMovement(2,2))
                        .add(new RotatedLinearMovement(test, 1.5f))
                        .add(new Position(position.x + pattern.offsets.get(i).offset.x, position.y + pattern.offsets.get(i).offset.y))
                       // .add(new CircularMovement())
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
