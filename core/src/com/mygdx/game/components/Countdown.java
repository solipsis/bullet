package com.mygdx.game.components;

import com.artemis.Component;

/**
 * Created by dave on 10/12/2016.
 */
public class Countdown extends Component {
    public int tRemaining;
    public int tReset;
    public int tStart;

    public Countdown() {}

    public Countdown(int tRemaining) {
        tStart = tRemaining;
        tReset = tRemaining;
        this.tRemaining = tRemaining;
    }
}
