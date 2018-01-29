package yi.jaein.musicplayer;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by a303-1 on 2018. 1. 22..
 * 2018_1_22 이재인 싱글턴패턴
 */

public class Recorder {

    static private Recorder instancce;
    int volume;
    MediaRecorder recorder = new MediaRecorder();

    private Recorder() {
    volume =5;
    }


    public static Recorder getInstancce() {
        if (instancce == null) {
            instancce = new Recorder();
            Log.d("Log", "객체 새성");

        } else {
            Log.d("Log", "이미 객체 새성");
        }
        return instancce;
    }

    public void startRecord() {
        Log.d("log", "녹음시작");
        try {
            File file = Environment.getExternalStorageDirectory();
            recorder.prepare();
            String path = file.getAbsolutePath() + "/sdcard/recorded.mp4";

            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //첫번째로 어떤 것으로 녹음할것인가를 설정한다. 마이크로 녹음을 할것이기에 MIC로 설정한다.
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            //이것은 파일타입을 설정한다.
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            //이것은 코덱을 설정
            recorder.setOutputFile(path);
            //저장될 파일을 저장한뒤

            recorder.start();
            //시작
        } catch (IllegalStateException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
    }




    public void stopRecord(){
        Log.d("log","녹음 중단");
        recorder.release();


    }
    public int getVolume(){
        Log.d("log","볼륨 얻어오기");
        return volume;

    }
    public void setVolume(int volume){
        Log.d("log","볼륨을 정하기");
        this.volume =volume;
    }

}
