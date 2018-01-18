package yi.jaein.musicplayer;

import java.util.ArrayList;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class FirstTab extends Fragment implements Runnable, View.OnClickListener, OnSeekBarChangeListener, OnCheckedChangeListener {
	/*
	* 디자인 패턴의 종류

세상엔 수많은 종류의 디자인 패턴이 있다. 그 내용을 모두 찾아내서 정리하자면 끝이 없을 것이다. 그래서 해당 포스트는 공부를 하면서 지속적으로 내용을 추가하고 보완해갈 것이다.



- 스트래티지 패턴 (strategy pattern)

교환 가능한 행동을 캡슐화하고 위임을 통해서 어떤 행동을 사용할지 결정한다.(x)

-팩토리 매서드 패턴
	 */
	
	private View mView;
	private CheckBox mListPlayRandom, mListPlayLoop;
	private SeekBar mListPlaySeek, mListPlayVolume;
	private AudioManager mAudioManager;
	
	private int mListNowPlaying;
	public ArrayList<SecondTabItem> mListItems;
	
	private boolean mMediaPause;
	private MediaPlayer mMediaPlayer;
	private boolean mMediaThreadCondition;

	ImageButton button1, button2, button3, button4, button5, button6;
	MediaPlayer music1, music2, music3, music4, music5, music6;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		button1 = (ImageButton) mView.findViewById(R.id.imageButton1);
		button2 = (ImageButton) mView.findViewById(R.id.imageButton2);
		button3 = (ImageButton) mView.findViewById(R.id.imageButton3);
		button4 = (ImageButton) mView.findViewById(R.id.imageButton4);
		button5 = (ImageButton) mView.findViewById(R.id.imageButton5);
		button6 = (ImageButton) mView.findViewById(R.id.imageButton6);


	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView = inflater.inflate(R.layout.activity_firsttab, null);
		// initialize;
		Init();
		// Set listener;
		SetListener();
		// Start setting
		stopPlaying();
		return mView;
	}
	
	@SuppressWarnings("static-access")
	private void Init() {
		// TODO Auto-generated method stub
		mListPlayRandom = (CheckBox) mView.findViewById(R.id.listPlayRandom);
		mListPlayLoop = (CheckBox) mView.findViewById(R.id.listPlayLoop);
		mListPlaySeek = (SeekBar) mView.findViewById(R.id.listPlaySeek);
		mListPlayVolume = (SeekBar) mView.findViewById(R.id.listPlayVolume);

		mAudioManager = (AudioManager) mView.getContext().getSystemService(mView.getContext().AUDIO_SERVICE);
		mListPlayVolume.setMax(mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
		mListPlayVolume.setProgress(mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

		mListNowPlaying = 0;
		mMediaPause = false;
		mMediaPlayer = new MediaPlayer();
		mMediaThreadCondition = false;

		Toast toast = Toast.makeText(getActivity(), "이미지를 한번 더 클릭하시면 ASMR이 정지됩니다.", Toast.LENGTH_LONG);
		toast.show();


		music1 = MediaPlayer.create(getActivity(), R.raw.a);
		music1.setLooping(true);
		music2 = MediaPlayer.create(getActivity(), R.raw.b);
		music2.setLooping(true);
		music3 = MediaPlayer.create(getActivity(), R.raw.c);
		music3.setLooping(true);
		music4 = MediaPlayer.create(getActivity(), R.raw.d);
		music4.setLooping(true);
		music5 = MediaPlayer.create(getActivity(), R.raw.e);
		music5.setLooping(true);
		music6 = MediaPlayer.create(getActivity(), R.raw.f);
		music6.setLooping(true);










	}


	private void SetListener() {
		// TODO Auto-generated method stub
		mListPlayRandom.setOnCheckedChangeListener(this);
		mListPlayLoop.setOnCheckedChangeListener(this);
		
		((Button) mView.findViewById(R.id.listPlayPlaying)).setOnClickListener(this);
		((Button) mView.findViewById(R.id.listPlayPause)).setOnClickListener(this);
		((Button) mView.findViewById(R.id.listPlayStop)).setOnClickListener(this);
		((Button) mView.findViewById(R.id.listPlayPrevious)).setOnClickListener(this);
		((Button) mView.findViewById(R.id.listPlayNext)).setOnClickListener(this);
        ((ImageButton) mView.findViewById(R.id.imageButton1)).setOnClickListener(this);
        ((ImageButton) mView.findViewById(R.id.imageButton2)).setOnClickListener(this);
        ((ImageButton) mView.findViewById(R.id.imageButton3)).setOnClickListener(this);
        ((ImageButton) mView.findViewById(R.id.imageButton4)).setOnClickListener(this);
        ((ImageButton) mView.findViewById(R.id.imageButton5)).setOnClickListener(this);
        ((ImageButton) mView.findViewById(R.id.imageButton6)).setOnClickListener(this);

		
		mListPlaySeek.setOnSeekBarChangeListener(this);
		mListPlayVolume.setOnSeekBarChangeListener(this);
	}
	
	private void SetTitle(String title) {
		// TODO Auto-generated method stub
		((TextView) mView.findViewById(R.id.listPlayTitle)).setText(title);
	}
	
	private void SetAlbumArt(Uri uri) {
		// TODO Auto-generated method stub
		if( uri == null )
			((ImageView) mView.findViewById(R.id.listPlayImage)).setImageResource(R.drawable.ic_launcher);
		else
			((ImageView) mView.findViewById(R.id.listPlayImage)).setImageURI(uri);
	}
	
	private void SetSeekBarSize(int size) {
		// TODO Auto-generated method stub
		mListPlaySeek.setMax(size);
	}
	
	private void SetPreviousPlaying() {
		// TODO Auto-generated method stub
		if( mListPlayLoop.isChecked() )				// �Ѱ� �ݺ�
			startPlaying( mListNowPlaying );
		else
			if( mListPlayRandom.isChecked() )		// ���� ���
				startPlaying( (int)(mListItems.size() * Math.random()) );
			else									// ���� ���
				if( mListNowPlaying - 1 >= 0 )
					startPlaying( mListNowPlaying - 1 );
				else
					startPlaying( mListItems.size() - 1 );
	}
	
	private void SetNextPlaying() {
		// TODO Auto-generated method stub
		if( mListPlayLoop.isChecked() )				// �Ѱ� �ݺ�
			startPlaying( mListNowPlaying );
		else
			if( mListPlayRandom.isChecked() )		// ���� ���
				startPlaying( (int)(mListItems.size() * Math.random()) );
			else									// ���� ���
				if( mListNowPlaying + 1 < mListItems.size() )
					startPlaying( mListNowPlaying + 1 );
				else
					startPlaying( 0 );
	}
	
	public void startPlaying( int position ) {
		// TODO Auto-generated method stub
		if( !mMediaPlayer.isPlaying() )
		{
			if( mListItems.size() == 0 )
			{
				toastMessage("재생할 곡이 없습니다");
				return;
			}
			if( mMediaPause )
			{
				unPausePlaying();
				return;
			}
			mListNowPlaying = position;
			SetTitle( mListItems.get(position).GetTitle() );
			SetAlbumArt( mListItems.get(position).GetImageUri() );
			SetSeekBarSize( mListItems.get(position).GetDuration() );
			mListPlaySeek.setProgress(0); 
			
			try {
				mMediaPlayer.reset();
				mMediaPlayer.setDataSource( mView.getContext(), mListItems.get(position).GetMusicUri() );    
				mMediaPlayer.prepare();
				mMediaPlayer.start();
				mMediaThreadCondition = true;
				new Thread(FirstTab.this).start();
				mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
					public void onCompletion(MediaPlayer mp) {
						// TODO
						// Do something when playing is completed
						stopPlaying();
						SetNextPlaying();
					}
				});

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while( mMediaThreadCondition )
		{
			try {
				Thread.sleep(500);
				if( mMediaPlayer.isPlaying() )
					mListPlaySeek.setProgress( mMediaPlayer.getCurrentPosition() );
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void unPausePlaying() {
		// TODO Auto-generated method stub
		if( mMediaPause )
		{
			mMediaPause = false;
			mMediaPlayer.start();
		}
	}
	
	private void pausePlaying() {
		// TODO Auto-generated method stub
		if( mMediaPlayer.isPlaying() )
		{
			mMediaPlayer.pause();
			mMediaPause = true;
			mMediaThreadCondition = false;
		}
	}
	
	public void stopPlaying() {
		// TODO Auto-generated method stub
		SetTitle("");
		SetAlbumArt(null);
		SetSeekBarSize(0);
		mMediaPlayer.stop();
		mMediaPause = false;
		mMediaThreadCondition = false;
	}
	
	private void toastMessage(String title) {
		// TODO Auto-generated method stub
		Toast.makeText(mView.getContext(), title, Toast.LENGTH_SHORT).show();
	}
	
	private String makeTimerText(int min, int sec) {
		// TODO Auto-generated method stub
		String strTemp = "";
		if( min < 10 )
			strTemp = "0";
		strTemp += min + "";
		strTemp += ":";
		if( sec < 10 )
			strTemp += "0";
		strTemp += sec + "";
		return strTemp;
	}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		switch( ((CheckBox)buttonView).getId() )
		{
		case R.id.listPlayRandom:
			if( isChecked )
			{
				((CheckBox)buttonView).setText("랜덤재생");
				toastMessage("랜덤재생");
			}
			else
			{
				((CheckBox)buttonView).setText("그냥재생");
				toastMessage("그냥재생");
			}
			break;
		case R.id.listPlayLoop:
			if( isChecked )
			{
				((CheckBox)buttonView).setText("반복재생");
				toastMessage("반복재생.");
			}
			else
			{
				((CheckBox)buttonView).setText("반복재생해제");
				toastMessage("반복재생해제");
			}
			break;


		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch( v.getId() )
		{
		case R.id.listPlayPlaying:
			if( mListItems.size() == 0 )
			{
				toastMessage("재생");
				return;
			}
			startPlaying(mListNowPlaying);
			break;
		case R.id.listPlayPause:
			pausePlaying();
			break;
		case R.id.listPlayStop:
			stopPlaying();
			break;
		case R.id.listPlayPrevious:
			stopPlaying();
			SetPreviousPlaying();
			break;
		case R.id.listPlayNext:
			stopPlaying();
			SetNextPlaying();
			break;
            case R.id.imageButton1:
                if (music1.isPlaying()) {
                    music1.pause();
                } else {
                    music1.start();
                }
                break;
            case R.id.imageButton2:
                if (music2.isPlaying()) {
                    music2.pause();
                } else {
                    music2.start();
                }
                break;
            case R.id.imageButton3:
                if (music3.isPlaying()) {
                    music3.pause();
                } else {
                    music3.start();
                }
                break;
            case R.id.imageButton4:
                if (music4.isPlaying()) {
                    music4.pause();
                } else {
                    music4.start();
                }
                break;
            case R.id.imageButton5:
                if (music5.isPlaying()) {
                    music5.pause();
                } else {
                    music5.start();
                }
                break;
            case R.id.imageButton6:
                if (music6.isPlaying()) {
                    music6.pause();
                } else {
                    music6.start();
                }
                break;

            default:
			break;
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekbar, int position, boolean press) {
		// TODO Auto-generated method stub
		switch( seekbar.getId() )
		{
		case R.id.listPlaySeek:
			if( press )
				mMediaPlayer.seekTo(position);
			int min = position / ( 60 * 1000 );
			int sec = ( position % (60 * 1000) ) / 1000;
			((TextView) mView.findViewById(R.id.listPlayDuration)).setText( makeTimerText(min, sec) );
			break;
		case R.id.listPlayVolume:
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, position, 0);
			break;
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		// unused;
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		// unused;		
	}

}
