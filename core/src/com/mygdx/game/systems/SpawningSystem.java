package com.mygdx.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.BulletGame;
import com.mygdx.game.components.*;
import com.mygdx.game.managers.AssetManager;

/**
 * Created by dave on 7/13/16.
 */
public class SpawningSystem extends EntityProcessingSystem {

    protected ComponentMapper<Position> positionMapper;
    protected ComponentMapper<Spawner> spawnerMapper;

    private SpriteBatch batch;
    private AssetManager assetManager = new AssetManager();


    public SpawningSystem() {
        super(Aspect.all(Position.class, Spawner.class));
    }


    @Override
    public void process(Entity e) {

        Spawner spawner = spawnerMapper.get(e);
        Position position = positionMapper.get(e);

        spawner.currTime -= 1;
        if (spawner.currTime < 1) {
            spawner.currTime = spawner.time;
            int bullet = BulletGame.world.create();
            BulletGame.world.edit(bullet)
                    .add(new LinearMovement(1, 1))
                    .add(new Position(position.x, position.y))
                   // .add(new CircularMovement())
                    //.add(new Spawner())
                    .add(new Sprite(30, 30, true));
        }
    }



}
