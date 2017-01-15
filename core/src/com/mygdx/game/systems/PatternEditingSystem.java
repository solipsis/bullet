package com.mygdx.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BulletGame;
import com.mygdx.game.BulletSpawn;
import com.mygdx.game.components.*;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.Gdx.input;

/**
 * Created by dave on 12/11/2016.
 */
public class PatternEditingSystem extends EntityProcessingSystem implements InputProcessor {

    protected ComponentMapper<Pattern> patternComponentMapper;
    protected ComponentMapper<EditBox> mEditBox;
    protected ComponentMapper<Edit> mEdit;

    boolean click = false;
    boolean delete;
    float xPos;
    float yPos;
    List<Integer> toDelete = new ArrayList();
    List<Integer> activeIds = new ArrayList();

    // todo: move this renderer lel. why the fuck i put this here
    private ShapeRenderer renderer;


    public PatternEditingSystem() {
        super(Aspect.all(Position.class, Pattern.class, EditBox.class, Edit.class));

        renderer = new ShapeRenderer();
        renderer.setProjectionMatrix(BulletGame.camera.combined);
    }

    public void cleanPatterns() {}

    @Override
    public void inserted(Entity e) {

        Pattern pattern = patternComponentMapper.get(e);
        EditBox editBox = mEditBox.get(e);


        for (BulletSpawn point : pattern.offsets) {
            int bullet = BulletGame.world.create();
            BulletGame.world.edit(bullet).add(new Position(editBox.rect.x + (editBox.rect.width/2) + point.offset.x, editBox.rect.y + (editBox.rect.height/2) + point.offset.y))
                    .add(new Sprite(30, 30, true));
        }



        BulletGame.debugEntitiesCreated += 1;
    }

    @Override
    protected void process(Entity e) {

        EditBox editBox = mEditBox.get(e);

        if (delete) {
            Pattern pattern = patternComponentMapper.get(e);
            if (!pattern.offsets.isEmpty()) {
                world.getEntity(mEdit.get(e).managedIds.get(mEdit.get(e).managedIds.size()-1)).deleteFromWorld();
                mEdit.get(e).managedIds.remove(mEdit.get(e).managedIds.size()-1);
                pattern.offsets.remove(pattern.offsets.size() - 1);
            }
            delete = false;
        }

        if (click && editBox.rect.contains(xPos, yPos)) {
            click = false;
            Pattern pattern = patternComponentMapper.get(e);


            Vector2 offset = new Vector2(xPos-editBox.rect.x-(editBox.rect.width/2), yPos-editBox.rect.y-(editBox.rect.height/2));
            BulletSpawn newSpawnPoint = new BulletSpawn(pattern.spawnIdCounter++, offset);



            pattern.offsets.add(newSpawnPoint);
            System.out.println("pattern updated");
            System.out.println("offsetX: ");

            Vector2 point = pattern.offsets.get(pattern.offsets.size()-1).offset;

            int patternBullet = BulletGame.world.create();
            BulletGame.world.edit(patternBullet).add(new Position(editBox.rect.x + (editBox.rect.width/2) + point.x, editBox.rect.y + (editBox.rect.height/2) + point.y))
                    .add(new Sprite(10, 10, true))
                    .add(new SelectBox(10,10, offset));
            mEdit.get(e).managedIds.add(patternBullet);
        }
        click = false;

        renderer.rect(editBox.rect.x, editBox.rect.y, editBox.rect.width, editBox.rect.height);

    }

    @Override
    public void initialize() {
        BulletGame.inputMultiplexer.addProcessor(this);
    }

    @Override
    protected void begin() {
     //   input.setInputProcessor(this);

        renderer.begin(ShapeRenderer.ShapeType.Line);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACKSPACE) {
            delete = true;
        }
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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

//        Vector3 gameMouse = BulletGame.camera.unproject(new Vector3(screenX, screenY, 0));
//        BulletGame.mousePos = gameMouse;
//        System.out.println("dragging");
//        System.out.println("gameX: " + gameMouse.x + "  gameY: " + gameMouse.y);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
     //   System.out.println("x: " + screenX + "  y: " + screenY);
//        Vector3 gameMouse = BulletGame.camera.unproject(new Vector3(screenX, screenY, 0));
//        BulletGame.mousePos = gameMouse;
//        System.out.println("move");
        //System.out.println("gameX: " + gameMouse.x + "  gameY: " + gameMouse.y);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {

            //LibGDX mouse wheel is inverted compared to lwjgl-basics
            NormalShaderTest.LIGHT_POS.z = Math.max(0f, NormalShaderTest.LIGHT_POS.z - (amount * 0.005f));
            System.out.println("New light Z: "+NormalShaderTest.LIGHT_POS.z);
            return true;


    }

    @Override
    protected void end() {
        renderer.end();
    }


    //@Subscribe

}
