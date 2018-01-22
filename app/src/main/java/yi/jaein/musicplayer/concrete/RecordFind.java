package yi.jaein.musicplayer.concrete;

import android.util.Log;

import yi.jaein.musicplayer.framework.Record;

/**
 * Created by a303-1 on 2018. 1. 18..
 */

public class RecordFind implements Record{
    @Override
    public void record() {
        System.out.print("녹음파일을 찾는다");
        Log.d("Log " ,"레코드 파인더");
    }
}
