package yi.jaein.musicplayer.framework;

import android.util.Log;

/**
 * Created by a303-1 on 2018. 1. 18..
 */
/*
    2018_01_18 이재인 팩토리메서드 패턴 적용 시작
 */
public abstract class RecordCreator {
    public Record create(){

        Record record;
        Log.d("Log " ,"녹음시작");

        //녹음을 먼저하고
        record = recordAsmr();

        //로그를 남기고
        createLogAsmr();

        //파일을 찾고
        findRecordedAsmr();
        Log.d("Log " ,"파일 찾기");

        //녹음한 걸 재생한다
        playAsmr();
        Log.d("Log " ,"재생");

        return record;
    }


    abstract protected Record recordAsmr();
    abstract protected void createLogAsmr();
    abstract protected void findRecordedAsmr();
    abstract protected void playAsmr();

}
