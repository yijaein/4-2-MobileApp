package yi.jaein.musicplayer;

import java.io.File;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.widget.Toast;
/*
	2018_01_15 다시 시작
 */

public class MainActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	public SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	public static Context mMainContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				updateMusicList();
			}
		});

		// User Function;
		mMainContext = this;
		deleteArtAlbum();
		Toast.makeText(mMainContext, "알림", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void updateMusicList() {
		// TODO Auto-generated method stub
		((FirstTab)mSectionsPagerAdapter.getItem(0)).mListItems = ((SecondTab)mSectionsPagerAdapter.getItem(1)).GetListItems();
	}
	
	@SuppressLint("SdCardPath")
	private void deleteArtAlbum() {
		// TODO Auto-generated method stub
		String path = "/sdcard/Android/data/com.android.providers.media/albumthumbs";
		File file = new File(path);
		String[] files = file.list();

		if( files != null )
			for( int idx = 0; idx < files.length; ++idx )
			{
				String filename = files[idx];
				File f = new File(path + "/" + filename);
				if (f.exists())
					f.delete();
			}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if( keyCode == KeyEvent.KEYCODE_BACK )
		{
			((FirstTab)mSectionsPagerAdapter.getItem(0)).stopPlaying();
			finish();
			android.os.Process.killProcess( android.os.Process.myPid() );
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		private Fragment firstTabFragment, secondTabFragment;
		
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			
			firstTabFragment = new FirstTab();
			secondTabFragment = new SecondTab();
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = null;
			switch( position )
			{
			case 0:
				fragment = firstTabFragment;
				break;
			case 1:
				fragment = secondTabFragment;
				break;
			default:
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 2 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}
	}

}
