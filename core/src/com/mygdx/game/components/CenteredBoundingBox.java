package com.mygdx.game.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by dave on 4/10/2017.
 */
public class CenteredBoundingBox extends Component {

    public Rectangle rect = new Rectangle();

    public CenteredBoundingBox() {}

    public CenteredBoundingBox(float height, float width) {
        rect.setHeight(height);
        rect.setWidth(width);
    }


}
