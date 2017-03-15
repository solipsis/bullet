package com.mygdx.game.systems;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.mygdx.game.components.Position;
import com.mygdx.game.components.Sprite;
import com.mygdx.game.components.ZigZag;

/**
 * Created by dave on 3/6/2017.
 */
public class ZigZagSystem extends EntityProcessingSystem {


    protected ComponentMapper<ZigZag> mZigZag;
    protected ComponentMapper<Position> mPosition;

    public ZigZagSystem() {
        super(Aspect.all(ZigZag.class, Position.class));
    }

    @Override
    protected void process(Entity e) {

        Position p = mPosition.get(e);
        ZigZag z = mZigZag.get(e);
        if (p.x >= z.xMin && p.x <= z.xMax) {
            p.x += z.delta * z.sign;
        } else {
            if (p.x < z.xMin) p.x = z.xMin;
            if (p.x > z.xMax) p.x = z.xMax;
            z.sign *= -1;
        }
    }
}
