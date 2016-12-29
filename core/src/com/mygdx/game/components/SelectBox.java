package com.mygdx.game.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by dave on 12/28/2016.
 */
public class SelectBox extends Component {
    public Rectangle rect;

    public SelectBox(){}

    public SelectBox(int width, int height) {
        rect = new Rectangle();
        rect.setWidth(width);
        rect.setHeight(height);
    }

}
