package yi.jaein.musicplayer;

import android.net.Uri;

public class SecondTabItem {
	private Uri mImageUri;
	private String mTitle;
	private String mContent;
	private int mDuration;
	private Uri mMusicUri;
	
	public SecondTabItem( Uri imageUri, String title, String content, int duration, Uri musicUri ) {
		// TODO Auto-generated constructor stub
		mImageUri = imageUri;
		mTitle = title;
		mContent = content;
		mDuration = duration;			// milliseconds;
		mMusicUri = musicUri;
	}
	
	public void SetImageUri( Uri imageUri ) {
		// TODO Auto-generated method stub
		mImageUri = imageUri;
	}
	
	public Uri GetImageUri() {
		// TODO Auto-generated method stub
		return mImageUri;
	}
	
	public void SetTitle( String title ) {
		// TODO Auto-generated method stub
		mTitle = title;
	}
	
	public String GetTitle() {
		// TODO Auto-generated method stub
		return mTitle;
	}
	
	public void SetContent( String content ) {
		// TODO Auto-generated method stub
		mContent = content;
	}
	
	public String GetContent() {
		// TODO Auto-generated method stub
		return mContent;
	}
	
	public void SetDuration( int duration ) {
		// TODO Auto-generated method stub
		mDuration = duration;
	}
	
	public int GetDuration() {
		// TODO Auto-generated method stub
		return mDuration;
	}
	
	public void SetMusicUri( Uri musicUri ) {
		// TODO Auto-generated method stub
		mImageUri = musicUri;
	}
	
	public Uri GetMusicUri() {
		// TODO Auto-generated method stub
		return mMusicUri;
	}
}
