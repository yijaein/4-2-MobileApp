package yi.jaein.musicplayer.concrete;

import yi.jaein.musicplayer.framework.Record;
import yi.jaein.musicplayer.framework.RecordCreator;

/**
 * Created by a303-1 on 2018. 1. 18..
 */

public class RecordPlayerCreator extends RecordCreator {


    @Override
    protected Record recordAsmr() {

        return new RecordPlayer();
    }

    @Override
    protected void createLogAsmr() {

    }

    @Override
    protected void findRecordedAsmr() {

    }

    @Override
    protected void playAsmr() {

    }
}
