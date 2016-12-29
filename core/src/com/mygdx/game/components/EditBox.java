package com.mygdx.game.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by dave on 12/14/2016.
 */
public class EditBox extends Component {

    public Rectangle rect;

    public EditBox() {

        rect = new Rectangle();
        rect.setPosition(100,100);
        rect.setWidth(100);
        rect.setHeight(100);
    }




}
