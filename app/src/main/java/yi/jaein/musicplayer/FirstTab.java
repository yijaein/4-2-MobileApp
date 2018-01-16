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
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class FirstTab extends Fragment implements Runnable, OnClickListener, OnSeekBarChangeListener, OnCheckedChangeListener {
	
	private View mView;
	private CheckBox mListPlayRandom, mListPlayLoop;
	private SeekBar mListPlaySeek, mListPlayVolume;
	private AudioManager mAudioManager;
	
	private int mListNowPlaying;
	public ArrayList<SecondTabItem> mListItems;
	
	private boolean mMediaPause;
	private MediaPlayer mMediaPlayer;
	private boolean mMediaThreadCondition;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
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
				toastMessage("����Ʈ�� �����ϴ�.");
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
				((CheckBox)buttonView).setText("���� ���");
				toastMessage("���� ���� ����Դϴ�.");
			}
			else
			{
				((CheckBox)buttonView).setText("���� ���");
				toastMessage("���� ���� ����Դϴ�.");
			}
			break;
		case R.id.listPlayLoop:
			if( isChecked )
			{
				((CheckBox)buttonView).setText("���� �ݺ�");
				toastMessage("���� �Ѱ� �ݺ� ����Դϴ�.");
			}
			else
			{
				((CheckBox)buttonView).setText("�Ѱ� �ݺ�");
				toastMessage("���� ���� �ݺ� ����Դϴ�.");
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
				toastMessage("����Ʈ�� �����ϴ�.");
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
