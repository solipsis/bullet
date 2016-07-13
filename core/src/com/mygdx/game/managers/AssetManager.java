package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by dave on 7/10/2016.
 */
public class AssetManager {
    public Texture temp;
    public Texture bullet;

    public AssetManager() {
        temp = new Texture(Gdx.files.internal("yukkuri.png"));
        bullet = new Texture(Gdx.files.internal("bullet.png"));
        temp.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

    }

    public Texture get() {
        return temp;
    }
    public Texture getBullet() { return bullet; }
}
