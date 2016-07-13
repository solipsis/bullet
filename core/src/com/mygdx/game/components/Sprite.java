package com.mygdx.game.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by dave on 7/10/2016.
 */
public class Sprite extends Component {
    public com.badlogic.gdx.graphics.g2d.Sprite sprite;
    public int width;
    public int height;

    public Sprite() {}

    public Sprite(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void init(Texture texture) {
        sprite = new com.badlogic.gdx.graphics.g2d.Sprite(texture);
        sprite.setSize(width, height);
        sprite.setPosition(0,0);
    }
}
