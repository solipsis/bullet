package com.mygdx.game.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by dave on 4/10/2017.
 */
public class ContainedLinearMovement extends Component {

    public Rectangle rect;
    public float deltaX = .5f;
    public float deltaY = .5f;

    public ContainedLinearMovement() {}

    public ContainedLinearMovement(float deltaX, float deltaY, Rectangle rect) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.rect = rect;
    }

}
