package com.mygdx.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mygdx.game.BulletGame;
import com.mygdx.game.components.Position;
import com.mygdx.game.components.Sprite;
import com.mygdx.game.managers.AssetManager;

import static com.mygdx.game.systems.NormalShaderTest.*;

/**
 * Created by dave on 7/10/2016.
 */
public class RenderingSystem extends EntityProcessingSystem {

    protected ComponentMapper<Sprite> spriteMapper;
    protected ComponentMapper<Position> positionMapper;
    public static NormalShaderTest normalShaderTest;

    Texture starNormals;

    public static ShaderProgram shader;
    private SpriteBatch batch;
    private AssetManager assetManager = new AssetManager();

    @Override
    public void inserted(Entity entity) {
        Sprite sprite = spriteMapper.get(entity.getId());
        if (sprite.deleteMe) {
            sprite.init(assetManager.getBullet());
        } else {
            sprite.init(assetManager.get());
        }
    }

    @Override
    protected void begin() {
      //  Gdx.gl.glClearColor(0, 0, 0.2f, 0);
    //    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        NormalShaderTest.LIGHT_POS.x = BulletGame.mousePos.x / BulletGame.WORLD_WIDTH * 2;
        NormalShaderTest.LIGHT_POS.y = BulletGame.mousePos.y / BulletGame.WORLD_HEIGHT;


        //send a Vector4f to GLSL
        shader.setUniformf("LightPos", LIGHT_POS);

        //bind normal map to texture unit 1
        starNormals.bind(1);

        //bind diffuse color to texture unit 0
        //important that we specify 0 otherwise we'll still be bound to glActiveTexture(GL_TEXTURE1)
        //rock.bind(0);
    }

    public RenderingSystem() {
        super(Aspect.all(Sprite.class, Position.class));





        starNormals = assetManager.getStarNormals();
        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(NormalShaderTest.VERT, NormalShaderTest.FRAG);
        if (!shader.isCompiled())
            throw new GdxRuntimeException("Could not compile shader: " + shader.getLog());
        // print warnings
        if (shader.getLog().length()!=0)
            System.out.println(shader.getLog());

        shader.begin();
        shader.setUniformi("u_normals", 1);
        shader.setUniformf("LightColor", LIGHT_COLOR.x, NormalShaderTest.LIGHT_COLOR.y, LIGHT_COLOR.z, LIGHT_INTENSITY);
        shader.setUniformf("AmbientColor", AMBIENT_COLOR.x, AMBIENT_COLOR.y, AMBIENT_COLOR.z, AMBIENT_INTENSITY);
        shader.setUniformf("Falloff", FALLOFF);
        shader.end();

        batch = new SpriteBatch(1000, shader);
        //batch = new SpriteBatch();
        batch.setShader(shader);
        batch.setProjectionMatrix(BulletGame.camera.combined);
    }

    @Override
    protected void process(Entity e) {

        Sprite sprite = spriteMapper.get(e);
        Position position = positionMapper.get(e);

        int spriteWidth = sprite.width;
        int spriteHeight = sprite.height;
        sprite.sprite.setPosition(position.x - (spriteWidth/2), position.y-(spriteHeight/2));
        if (BulletGame.beatCount > 0) {
         //   sprite.sprite.setScale(1 + (float)((float)BulletGame.beatCount/50.0f), 1+ (float)((float)BulletGame.beatCount/50.0f));
         //   sprite.sprite.setRotation(BulletGame.beatCount);
            //sprite.sprite.setColor(sprite.sprite.getColor().r + BulletGame.beatCount, sprite.sprite.getColor().g, sprite.sprite.getColor().b, 1-(BulletGame.beatCount));
        }
        sprite.sprite.getTexture().bind(0);
        sprite.sprite.draw(batch);
        //batch.draw(sprite.sprite.getTexture(), sprite.sprite.getX(), sprite.sprite.getY());
    }

    @Override
    protected void end() {
        batch.end();
        BulletGame.beatCount--;
    }
}
