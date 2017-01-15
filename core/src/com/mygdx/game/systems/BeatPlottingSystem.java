package com.mygdx.game.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.BulletGame;
import com.mygdx.game.components.Song;

/**
 * Created by dave on 1/8/2017.
 */
public class BeatPlottingSystem extends EntityProcessingSystem {

    protected ComponentMapper<Song> mSong;
    ShapeRenderer renderer;

    int duration = 0;

    public BeatPlottingSystem() {
        super(Aspect.all(Song.class));

        renderer = new ShapeRenderer();
        renderer.setProjectionMatrix(BulletGame.camera.combined);
    }

    @Override
    protected void begin() {
        renderer.setColor(0, 1, 0, 1);
        renderer.begin(ShapeRenderer.ShapeType.Line);
    }


    @Override
    protected void process(Entity e) {

        Song song = mSong.get(e);
        duration++;
       // System.out.println(song.graphData[0].length);
        float xScale = BulletGame.WORLD_WIDTH / song.pretty.size();
        Vector2 prev = new Vector2(0,0);
   //     System.out.println(song.durationInSeconds);
        for (int x = 0; x < song.pretty.size(); x++) {
            int i = song.pretty.get(x);
            if (Math.abs(i) > 15000) {
                if ((int)((BulletGame.currentSong.getPosition() / (song.durationInSeconds)) * 10000) == (int)(((float)x / (float)song.pretty.size()) * 10000)) {
                 //   System.out.println("A: " + (int)((BulletGame.currentSong.getPosition() / (song.durationInSeconds)) * 1000));
                 //   System.out.println("B: " + (int)(((float)x / (float)song.pretty.size()) * 1000));
                    BulletGame.beat = true;
                    if (BulletGame.beatCount <= 0) {
                        BulletGame.beatCount = 25;
                    }
                }

                renderer.setColor(1,0,1,1);
                renderer.line(prev.x, prev.y-200, prev.x + xScale, (i / 1000000) + (BulletGame.WORLD_HEIGHT / 5) -200 );
            } else {
                renderer.setColor(0,1,0,0.2f);
            }


            prev.x = prev.x + xScale;
            prev.y = (i / 100) + (BulletGame.WORLD_HEIGHT / 5);
        }

        float x = (float)((BulletGame.currentSong.getPosition()) / (song.durationInSeconds)) * (float)BulletGame.WORLD_WIDTH;
      //  System.out.println(x);
        renderer.setColor(1,0,0,1);
        renderer.line(x, 300, x+1, 10);

    }

    @Override
    protected void end() {
        renderer.end();
    }
}
