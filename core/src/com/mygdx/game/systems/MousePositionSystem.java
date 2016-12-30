package com.mygdx.game.systems;

import com.artemis.BaseSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.BulletGame;


/**
 * Created by dave on 12/29/2016.
 */
// TODO: convert to general Debug info???
public class MousePositionSystem extends BaseSystem {

    BitmapFont font = new BitmapFont();
    SpriteBatch batch;

    public MousePositionSystem() {
        batch = new SpriteBatch();
        batch.setProjectionMatrix(BulletGame.camera.combined);
    }

    @Override
    protected void processSystem() {

    }

    @Override
    protected void begin() {
        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 990);
        font.draw(batch, "Total Entities: " + BulletGame.debugEntitiesCreated, 150, 990);
        font.draw(batch, "X: " + BulletGame.mousePos.x + " Y: " + BulletGame.mousePos.y, 50, 50);
     //   font.draw(batch, "LightPos X: " + NormalShaderTest.LIGHT_POS.x + " Y: " + NormalShaderTest.LIGHT_POS.y + " Z: " + NormalShaderTest.LIGHT_POS.z, 40, 900);
    }

    @Override
    protected  void end() {
       batch.end();
    }
}
