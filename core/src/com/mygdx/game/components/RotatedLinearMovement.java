package com.mygdx.game.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by dave on 12/10/2016.
 */
public class RotatedLinearMovement extends Component {

    public Vector2 move;

    public RotatedLinearMovement(float rotation, float magnitude) {
        move = new Vector2(magnitude,magnitude);
        move = move.rotate(rotation);

    }

    public RotatedLinearMovement() {}


}
