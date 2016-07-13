package com.mygdx.game.components;

import com.artemis.Component;

/**
 * Created by dave on 7/10/2016.
 */
public class Position extends Component{
    public float x;
    public float y;

    public Position() {}

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
