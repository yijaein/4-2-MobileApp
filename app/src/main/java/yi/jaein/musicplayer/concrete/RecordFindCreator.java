package yi.jaein.musicplayer.concrete;

import android.util.Log;

import yi.jaein.musicplayer.MainActivity;
import yi.jaein.musicplayer.framework.Record;
import yi.jaein.musicplayer.framework.RecordCreator;

/**
 * Created by a303-1 on 2018. 1. 18..
 */

public class RecordFindCreator extends RecordCreator{

    @Override
    protected Record recordAsmr() {
        Log.d("Log " ,"녹음");
        return new RecordFind();
    }

    @Override
    protected void createLogAsmr() {
        Log.d("Log " ,"created ASMR");
    }

    @Override
    protected void findRecordedAsmr() {
        Log.d("Log " ,"녹음한 걸 찾는다");
    }

    @Override
    protected void playAsmr() {
        Log.d("Log " ,"재생");

    }
}
