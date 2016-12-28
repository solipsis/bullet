package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by dave on 12/28/2016.
 */
public class BulletSpawn {

    public int id;
    public Vector2 offset;

    public BulletSpawn(int id, Vector2 offset) {
        this.id = id;
        this.offset = offset;
    }
}
