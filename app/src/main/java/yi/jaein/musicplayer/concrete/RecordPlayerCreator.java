package yi.jaein.musicplayer.concrete;

import android.util.Log;

import yi.jaein.musicplayer.framework.Record;
import yi.jaein.musicplayer.framework.RecordCreator;

/**
 * Created by a303-1 on 2018. 1. 18..
 */

public class RecordPlayerCreator extends RecordCreator {


    @Override
    protected Record recordAsmr() {
        Log.d("Log " ,"레코드");
        return new RecordPlayer();
    }

    @Override
    protected void createLogAsmr() {
        Log.d("Log " ,"로그를 남긴다");
    }

    @Override
    protected void findRecordedAsmr() {
        Log.d("Log " ,"녹음된 위치를 찾는다");
    }

    @Override
    protected void playAsmr() {
        Log.d("Log " ,"재생한다");
    }
}
