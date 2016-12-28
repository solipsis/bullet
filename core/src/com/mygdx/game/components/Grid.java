package com.mygdx.game.components;

import com.artemis.Component;

/**
 * Created by dave on 12/23/16.
 */
public class Grid extends Component {

    float height;
    float width;
    float xFreq;
    float yFreq;

    public Grid(float h, float w, float xf, float yf) {
        height = h;
        width = w;
        xFreq = xf;
        yFreq = yf;
    }
}
