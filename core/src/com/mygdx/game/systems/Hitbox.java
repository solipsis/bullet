package com.mygdx.game.systems;

import com.artemis.Component;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by dave on 12/30/2016.
 */
public class Hitbox extends Component {

    public Rectangle rect;
    public float xOffset;
    public float yOffset;

    public Hitbox() {}

    public Hitbox(float width, float height, float xOffset, float yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        rect = new Rectangle(0,0,width,height);
    }
}
