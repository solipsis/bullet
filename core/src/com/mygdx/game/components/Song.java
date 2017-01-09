package com.mygdx.game.components;

import com.artemis.Component;

import javax.sound.sampled.AudioInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dave on 1/8/2017.
 */
public class Song extends Component {

    public byte[] bytes;
    public int[][] graphData;
    public double durationInSeconds;
    public List<Integer> pretty = new ArrayList<Integer>();

    public Song() {}

    public Song(AudioInputStream audioInputStream) {
        bytes = new byte[(int) (audioInputStream.getFrameLength()) * (audioInputStream.getFormat().getFrameSize())];

        durationInSeconds = (audioInputStream.getFrameLength()+0.0) / audioInputStream.getFormat().getFrameRate();

        try {
            audioInputStream.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        graphData = getUnscaledAmplitude(bytes, 1);
    }


    public int[][] getUnscaledAmplitude(byte[] eightBitByteArray, int nbChannels) {
        int[][] toReturn = new int[nbChannels][eightBitByteArray.length / (2 * nbChannels)];
        int index = 0;

        int blah = 999;
        for (int audioByte = 0; audioByte < eightBitByteArray.length;) {
            for (int channel = 0; channel < nbChannels; channel++) {
                // Byte to sample conversion
                int low = (int) eightBitByteArray[audioByte];
                audioByte++;
                int high = (int) eightBitByteArray[audioByte];
                audioByte++;
                int sample = (high << 8) + (low & 0x00ff);
                if (++blah % 500 == 0) {
                    pretty.add(sample);
                    blah = 0;
                }
                toReturn[channel][index] = sample;

            }
            index++;
        }
        return toReturn;
    }

}
