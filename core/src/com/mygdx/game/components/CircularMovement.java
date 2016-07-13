package com.mygdx.game.components;

import com.artemis.Component;

/**
 * Created by dave on 7/12/2016.
 */
public class CircularMovement extends Component {
    public float angle = 0;
    public float rate = 1f;
    public float radius = 150;
    public float prev_x = radius * (float)Math.sin(Math.toRadians(angle));
    public float prev_y = radius * (float)Math.cos(Math.toRadians(angle));

}
