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
        System.out.print("녹음");
        return new RecordFind();
    }

    @Override
    protected void createLogAsmr() {
        Log.d("Log " ,"created ASMR");
    }

    @Override
    protected void findRecordedAsmr() {

    }

    @Override
    protected void playAsmr() {


    }
}
