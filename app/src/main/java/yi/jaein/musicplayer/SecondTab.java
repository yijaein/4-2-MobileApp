package yi.jaein.musicplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SecondTab extends Fragment implements OnClickListener, OnItemClickListener {

	private final int ADD_FILE_REQUEST				=	1000;
	private final int ADD_DIR_REQUEST				=	1001;

	private View mView;
	private ListView mList;
	private ListAdapter mAdapter;
	private ArrayList<SecondTabItem> mListItems;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView = inflater.inflate(R.layout.activity_secondtab, null);
		// Initialize;
		Init();
		// enroll listener;
		SetListener();
		return mView;
	}

	private void Init() {
		// TODO Auto-generated method stub
		mListItems = new ArrayList<SecondTabItem>();
		((FirstTab)((MainActivity) MainActivity.mMainContext).mSectionsPagerAdapter.getItem(0)).mListItems = mListItems;
		mList = (ListView) mView.findViewById(R.id.list);
		mAdapter = new SecondTabAdapter(mView.getContext(), R.layout.list_secondtab, mListItems);
		mList.setAdapter( mAdapter );
	}

	private void SetListener() {
		// TODO Auto-generated method stub
		((Button) mView.findViewById(R.id.listaddFile)).setOnClickListener(this);
		((Button) mView.findViewById(R.id.listaddDir)).setOnClickListener(this);
		((Button) mView.findViewById(R.id.listClear)).setOnClickListener(this);
		mList.setOnItemClickListener(this);
	}

	private void SetList() {
		// TODO Auto-generated method stub
		((BaseAdapter) mAdapter).notifyDataSetChanged();
		((MainActivity) MainActivity.mMainContext).updateMusicList();
	}

	private void getMusicMetaInfo(String filePath) {
		// TODO Auto-generated method stub
		StringTokenizer token = new StringTokenizer(filePath, "/");
		token.nextToken();
		filePath = token.nextToken() + "/";
		filePath += token.nextToken();
		filePath = "/storage/emulated/0/" + filePath;

		String[] projection = {
				MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.YEAR,
				MediaStore.Audio.Media.TITLE,
				MediaStore.Audio.Media.ARTIST,
				MediaStore.Audio.Media.ARTIST_ID,
				MediaStore.Audio.Media.ALBUM,
				MediaStore.Audio.Media.ALBUM_ID,
				MediaStore.Audio.Media.DURATION
		};
		Cursor cursor = mView.getContext().getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, 
				projection, 
				android.provider.MediaStore.Audio.Media.DATA + " like ? ",   
						new String[] {"%" + filePath + "%"}, 
						null
				);
		cursor.moveToFirst();

		boolean bOverlap = false;
		for( int idx = 0; idx < mListItems.size(); ++idx )
			if( mListItems.get(idx).GetTitle().equals(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))) )
			{
				bOverlap = true;
				break;
			}
		if( !bOverlap )			// �ߺ����� ���� ���븸 �߰�;
		{
			Uri imageUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
			String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
			int year = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.YEAR));
			String dateString = "";
			try {
				dateString = new String(year+"").substring(0, 4) + "." + new String(year+"").substring(4, 6) + "." + new String(year+"").substring(6, 8);
			} catch(Exception e) {
				e.printStackTrace();
			}
			String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
			String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
			int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
			Uri musicUri = Uri.withAppendedPath(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "" + cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
			
			mListItems.add(new SecondTabItem(imageUri, title, dateString + "/" + artist + "/" + album, duration, musicUri));
		}
		cursor.close();
	}
	
	public ArrayList<SecondTabItem> GetListItems() {
		// TODO Auto-generated method stub
		return mListItems;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		String path = "";

		switch( requestCode )
		{
		case ADD_FILE_REQUEST:
			if( resultCode == 0 )
			{
				path = data.getExtras().getString("FilePath");
				getMusicMetaInfo( path );
				SetList();
			}
			break;
		case ADD_DIR_REQUEST:
			if( resultCode == 0 )
			{
				path = data.getExtras().getString("FilePath");
				File fileList[] = new File(path).listFiles();
				for(File tempFile : fileList) 
				{
					if(tempFile.isFile()) 
					{
						getMusicMetaInfo( tempFile.getPath() );
					}
				}
				SetList();
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
		case R.id.listaddFile:
		{
			Intent intent = new Intent(mView.getContext(), ExplorerActivity.class);
			intent.putExtra("ExplorerType", 0);
			startActivityForResult(intent, ADD_FILE_REQUEST);
		}
		break;
		case R.id.listaddDir:
		{
			Intent intent = new Intent(mView.getContext(), ExplorerActivity.class);
			intent.putExtra("ExplorerType", 1);
			startActivityForResult(intent, ADD_DIR_REQUEST);
		}
		break;
		case R.id.listClear:
		{
			mListItems.clear();
			SetList();
			break;
		}
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long resource) {
		// TODO Auto-generated method stub
		((FirstTab)((MainActivity) MainActivity.mMainContext).mSectionsPagerAdapter.getItem(0)).stopPlaying();
		((FirstTab)((MainActivity) MainActivity.mMainContext).mSectionsPagerAdapter.getItem(0)).startPlaying(position);
	}

}
