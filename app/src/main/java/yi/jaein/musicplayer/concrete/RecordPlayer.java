package yi.jaein.musicplayer.concrete;

import yi.jaein.musicplayer.framework.Record;

/**
 * Created by a303-1 on 2018. 1. 18..
 */

public class RecordPlayer implements Record{
    @Override
    public void record() {
        System.out.print("녹음");

    }
}
