package com.mygdx.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BulletGame;
import com.mygdx.game.components.Pattern;
import com.mygdx.game.components.Position;

/**
 * Created by dave on 12/11/2016.
 */
public class PatternEditingSystem extends EntityProcessingSystem implements InputProcessor {

    protected ComponentMapper<Pattern> patternComponentMapper;
    boolean click = false;
    float xPos;
    float yPos;

    @Override
    protected void process(Entity e) {

        if (click) {
            click = false;
            Pattern pattern = patternComponentMapper.get(e);
            pattern.offsets.add(new Vector2(xPos, yPos));
            System.out.println("pattern updated");
        }

    }

    @Override
    public void initialize() {
        Gdx.input.setInputProcessor(this);
    }



    public PatternEditingSystem() {
        super(Aspect.all(Position.class, Pattern.class));
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
       // if (Gdx.input.justTouched()) {
            Vector3 gameMouse = BulletGame.camera.unproject(new Vector3(screenX, screenY, 0));
            xPos = gameMouse.x;
            yPos = gameMouse.y;
            click = true;
         System.out.println("gameX: " + gameMouse.x + "  gameY: " + gameMouse.y);
      //  }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
     //   System.out.println("x: " + screenX + "  y: " + screenY);
     //   Vector3 gameMouse = BulletGame.camera.unproject(new Vector3(screenX, screenY, 0));
     //   System.out.println("gameX: " + gameMouse.x + "  gameY: " + gameMouse.y);
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


    //@Subscribe

}
