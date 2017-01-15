package com.mygdx.game.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by dave on 12/28/2016.
 */
public class SelectBox extends Component {
    public Rectangle rect;
    public Vector2 delta;
    public Vector2 offsetVec; // KILL ME
    public boolean dirty;

    public SelectBox(){}

    public SelectBox(int width, int height) {
        rect = new Rectangle();
        delta = new Vector2();
        offsetVec = new Vector2();
        rect.setWidth(width);
        rect.setHeight(height);
    }

    public SelectBox(int width, int height, Vector2 offsetVec) {

        rect = new Rectangle();
        delta = new Vector2();
        this.offsetVec = offsetVec;
        rect.setWidth(width);
        rect.setHeight(height);
    }

}
