package com.mygdx.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BulletGame;
import com.mygdx.game.components.Position;
import com.mygdx.game.components.SelectBox;
import com.mygdx.game.idk.SpacialHasher;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.List;
import java.util.Set;

import static com.badlogic.gdx.Gdx.input;

/**
 * Created by dave on 12/28/2016.
 */
public class SelectSystem extends EntityProcessingSystem implements InputProcessor {

    boolean drag = false; // why
    boolean down = false;
    protected ComponentMapper<SelectBox> mSelectBox;
    protected ComponentMapper<Position> mPosition;
    private int selectedEntity;
    private SpacialHasher hasher;
    Vector3 gameMouse = new Vector3();

    private ShapeRenderer renderer;

    public SelectSystem() {
        super(Aspect.all(SelectBox.class, Position.class));

        renderer = new ShapeRenderer();
        hasher = new SpacialHasher();
        renderer.setProjectionMatrix(BulletGame.camera.combined);
    }

    @Override
    protected void process(Entity e) {
        Position position = mPosition.get(e);
        SelectBox selectBox = mSelectBox.get(e);


        selectBox.rect.setPosition(position.x-selectBox.rect.width/2.0f, position.y-selectBox.rect.height/2.0f);

        float x = selectBox.rect.x;
        float y = selectBox.rect.y;
        float width = selectBox.rect.width;
        float height = selectBox.rect.height;
        int id = e.getId();


        hasher.insert(x, y + height, id);
        hasher.insert(x,y,id);
        hasher.insert(x+width,y,id);
        hasher.insert(x+width,y+height, id);


       // renderer.rect(selectBox.rect.x, selectBox.rect.y, selectBox.rect.width, selectBox.rect.height);

    }

    @Override
    protected void initialize() {
        BulletGame.inputMultiplexer.addProcessor(this);
    }

    @Override
    protected void begin() {
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(1,0,0,1);
        hasher.init();
       // input.setInputProcessor(this);

    }

    @Override
    protected  void end() {

        for (int i = 0; i < hasher.cols; i++) {
            for (int j = 0; j < hasher.rows; j++) {
                renderer.rect(i*hasher.bucketSize, j * hasher.bucketSize, hasher.bucketSize, hasher.bucketSize);
            }
        }

        Set<Integer> nearby = hasher.getNearbyEntities(gameMouse.x, gameMouse.y);
        for (int i : nearby) {
            SelectBox selectBox = world.getEntity(i).getComponent(SelectBox.class);
            Position position = world.getEntity(i).getComponent(Position.class);
            if (selectBox.rect.contains(gameMouse.x, gameMouse.y)) {
                if (drag) {
                    selectBox.delta.x = gameMouse.x - position.x;
                    selectBox.delta.y = gameMouse.y - position.y ;
                    selectBox.dirty = true;

                    position.x = gameMouse.x;
                    position.y = gameMouse.y;
                }
                renderer.setColor(1,1,0,1);
            } else {
                renderer.setColor(0, 1, 0, 0.5f);
            }

            renderer.rect(selectBox.rect.x, selectBox.rect.y, selectBox.rect.width, selectBox.rect.height);
        }
        renderer.end();
    }

    //*****************************************************************************
    // Input handling
    //*****************************************************************************
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
        down = true;
        gameMouse = BulletGame.camera.unproject(new Vector3(screenX, screenY, 0));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        drag = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        drag = true;
        gameMouse = BulletGame.camera.unproject(new Vector3(screenX, screenY, 0));
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        gameMouse = BulletGame.camera.unproject(new Vector3(screenX, screenY, 0));
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}