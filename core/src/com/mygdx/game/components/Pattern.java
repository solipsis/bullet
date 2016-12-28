package com.mygdx.game.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.BulletSpawn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dave on 12/13/16.
 */
public class Pattern extends Component {
    public List<BulletSpawn> offsets = new ArrayList<BulletSpawn>();
    public int id;
    public int spawnIdCounter = 1;



    public Pattern() {

    }

    /*
    public Pattern(List<Vector2> offsets) {
        this.offsets = offsets;
    }
    */


}
