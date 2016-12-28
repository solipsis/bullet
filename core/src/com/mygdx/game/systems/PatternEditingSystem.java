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
import com.mygdx.game.components.EditBox;
import com.mygdx.game.components.Pattern;
import com.mygdx.game.components.Position;
import com.mygdx.game.components.Sprite;

import static com.badlogic.gdx.Gdx.input;

/**
 * Created by dave on 12/11/2016.
 */
public class PatternEditingSystem extends EntityProcessingSystem implements InputProcessor {

    protected ComponentMapper<Pattern> patternComponentMapper;
    protected ComponentMapper<EditBox> mEditBox;
    boolean click = false;
    boolean delete;
    float xPos;
    float yPos;

    // todo: move this renderer lel. why the fuck i put this here
    private ShapeRenderer renderer;


    public PatternEditingSystem() {
        super(Aspect.all(Position.class, Pattern.class, EditBox.class));

        renderer = new ShapeRenderer();
        renderer.setProjectionMatrix(BulletGame.camera.combined);
    }

    @Override
    public void inserted(Entity e) {

        Pattern pattern = patternComponentMapper.get(e);
        EditBox editBox = mEditBox.get(e);

        for (Vector2 point : pattern.offsets) {
            int bullet = BulletGame.world.create();
            BulletGame.world.edit(bullet).add(new Position(editBox.rect.x + (editBox.rect.width/2) + point.x, editBox.rect.y + (editBox.rect.height/2) + point.y))
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
                pattern.offsets.remove(pattern.offsets.size() - 1);
            }
            delete = false;
        }

        if (click && editBox.rect.contains(xPos, yPos)) {
            click = false;
            Pattern pattern = patternComponentMapper.get(e);
            pattern.offsets.add(new Vector2(xPos-editBox.rect.x-(editBox.rect.width/2), yPos-editBox.rect.y-(editBox.rect.height/2)));
            System.out.println("pattern updated");
            System.out.println("offsetX: ");

            Vector2 point = pattern.offsets.get(pattern.offsets.size()-1);
            int patternBullet = BulletGame.world.create();
            BulletGame.world.edit(patternBullet).add(new Position(editBox.rect.x + (editBox.rect.width/2) + point.x, editBox.rect.y + (editBox.rect.height/2) + point.y))
                    .add(new Sprite(10, 10, true));
        }
        click = false;

        renderer.rect(editBox.rect.x, editBox.rect.y, editBox.rect.width, editBox.rect.height);

    }

    @Override
    public void initialize() {
        input.setInputProcessor(this);
    }

    @Override
    protected void begin() {

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
        Vector3 gameMouse = BulletGame.camera.unproject(new Vector3(screenX, screenY, 0));
        //System.out.println("gameX: " + gameMouse.x + "  gameY: " + gameMouse.y);
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    protected void end() {
        renderer.end();
    }


    //@Subscribe

}
