package com.mygdx.game.components;


import com.artemis.Component;

/**
 * Created by dave on 3/6/2017.
 */
public class ZigZag extends Component {

    public float xMin;
    public float xMax;
    public float delta;
    public float sign = 1;

    public ZigZag() {}

    public ZigZag(float xMin, float xMax, float delta) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.delta = delta;
    }


}
