package com.mygdx.game.components;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by dave on 1/14/2017.
 */
public class SelectedObjectUpdatingSystem extends EntityProcessingSystem {

    protected ComponentMapper<SelectBox> mSelectBox;

    // THIS NAME IS LITERALLY CANCER HITLER
    public SelectedObjectUpdatingSystem() {
        super(Aspect.all(SelectBox.class));
    }

    @Override
    protected void process(Entity e) {

        SelectBox box = mSelectBox.get(e);

        if (box.dirty) {
            float prevX = box.offsetVec.x;
            float prevY = box.offsetVec.y;
            box.offsetVec.set(prevX + box.delta.x, prevY + box.delta.y);
            box.dirty = false;
            box.delta = new Vector2(0,0);
        }

    }
}
