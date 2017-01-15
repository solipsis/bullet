package com.mygdx.game.idk;

import com.mygdx.game.BulletGame;

import java.util.*;

/**
 * Created by dave on 1/13/2017.
 */
public class SpacialHasher {

    public final int bucketSize = 100;
    public int rows = (int)(BulletGame.WORLD_WIDTH / bucketSize);
    public int cols = (int)(BulletGame.WORLD_HEIGHT / bucketSize);
    public Map<Integer, Set<Integer>> buckets = new HashMap<Integer, Set<Integer>>(rows * cols);

    public SpacialHasher() {
        init();
    }

    // call this every frame
    public void init() {
        for (int i = 0; i < rows * cols; i++) {
            buckets.put(i, new HashSet<Integer>());
        }
    }

    public void insert(float x, float y, int id) {
        int bucket = getBucket(x, y);
        if (bucket > 99 || bucket < 0) {
            //System.out.println("panic");
        } else {
            buckets.get(bucket).add(id);
        }
    }

    private int getBucket(float x, float y) {
        return (int)Math.floor(x / bucketSize) + (int)Math.floor((y / bucketSize)) * cols;
    }

    public Set<Integer> getNearbyEntities(float x, float y) {
        return buckets.get(getBucket(x,y));
    }
}
