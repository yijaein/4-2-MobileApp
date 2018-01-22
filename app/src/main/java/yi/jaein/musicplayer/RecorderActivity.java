package yi.jaein.musicplayer;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by a303-1 on 2018. 1. 22..
 */

public class RecorderActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recorder_activity);
        Recorder recorder  = Recorder.getInstancce();
        recorder.startRecord();

        recorder.getVolume();
        recorder.setVolume(10);
        recorder.getVolume();

        recorder.stopRecord();
    }
}
