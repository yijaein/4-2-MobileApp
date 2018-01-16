/**
 * @author Kim Woo Hyeon
 * ExplorerActivity.java
 */

package yi.jaein.musicplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import yi.jaein.musicplayer.R.string;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SdCardPath")
public class ExplorerActivity extends ListActivity {

	private List<String> mItems = null;
	private List<String> mPaths = null;
	private List<String> mSavePath = null;
	private TextView mNowLocation;
	private String mSelectedFilePath = "";
	private int mType = -1;
	private int mPosition;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_explorer);

//		((LinearLayout) findViewById(R.id.explorerBackground)).setBackgroundDrawable(((MainActivity)MainActivity.mMainContext).GetBackgroundImage());
		((TextView) findViewById(android.R.id.empty)).setText("No Data");

		mType = getIntent().getExtras().getInt("ExplorerType");			// 0 : File, 1 : Directory
		mSelectedFilePath = "";
		mNowLocation = (TextView)findViewById(R.id.explorerPath);
		mSavePath = new ArrayList<String>();

		mSavePath.add( "/sdcard/" );
		getDir( "/sdcard/" );
	}

	private void getDir(String dirPath) {
		// TODO Auto-generated method stub
		mNowLocation.setText("Location: " + dirPath);

		mItems = new ArrayList<String>();
		mPaths = new ArrayList<String>();

		File f = new File(dirPath);
		File[] files = f.listFiles();
		String strTemp = mSavePath.get( mSavePath.size() - 1 );
		if( strTemp != dirPath )
			mSavePath.add( dirPath );

		for( int idx = 0; idx < files.length; ++idx )
		{
			File file = files[idx];
			mPaths.add(file.getPath());

			if(file.isDirectory())
				mItems.add(file.getName() + "/");
			else
				mItems.add(file.getName());
		}

		ArrayAdapter<String> fileList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mItems);
		setListAdapter(fileList); 
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		final File file = new File(mPaths.get(position));

		if (file.isDirectory())
		{
			if(file.canRead())
				if( mType == 0 )
					getDir(mPaths.get(position));
				else
				{
					boolean extensionEqual = false;
					mPosition = position;
					
					File fileList[] = file.listFiles();
					for(File tempFile : fileList) 
					{
						if(tempFile.isFile()) 
						{
							int pos = tempFile.getName().lastIndexOf(".");
							String fileExtension = tempFile.getName().substring( pos + 1 );
							extensionEqual = fileExtension.equals("mp3") || fileExtension.equals("wav") || fileExtension.equals("wma") || fileExtension.equals("mid");
							if( extensionEqual )
								break;
						}
					}
					if( extensionEqual )				// ???? ???? ???? ?????? ???? ???
					{
						new AlertDialog.Builder(this)
						.setIcon(R.drawable.ic_launcher)
						.setTitle("???? ????")
						.setMessage("??????? ?????? ??????? ????????????")
						.setNegativeButton(string.ok_button, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								mSelectedFilePath = file.getPath();
								setResult(0, getIntent().putExtra("FilePath", mSelectedFilePath));
								finish();
							}
						})
						.setPositiveButton(string.cancel_button, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								boolean haveDir = false;
								File []fileList = file.listFiles();
								for(File tempFile : fileList) 
								{
									if(tempFile.isDirectory()) 
									{
										haveDir = true;
										break;
									}
								}
								if( haveDir )
									getDir(mPaths.get(mPosition));
							}
						})
						.show();
					}
					else
						getDir(mPaths.get(position));
				}
			else
			{
				new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_launcher)
				.setTitle("[" + file.getName() + "]" + "?? ?? ?? ???????")
				.setPositiveButton(string.ok_button, null)
				.show();
			}
		}
		else {
			int pos = file.getName().lastIndexOf(".");
			String fileExtension = file.getName().substring( pos + 1 );
			// Check extension;
			boolean extensionEqual = false;
			if( mType == 0 )
				extensionEqual = fileExtension.equals("mp3") || fileExtension.equals("wav") || fileExtension.equals("wma") || fileExtension.equals("mid");
			if( extensionEqual )
			{
				mSelectedFilePath = file.getPath();
				
				new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_launcher)
				.setTitle("???? ????")
				.setMessage("[" + file.getName() + "]" + "?? ?????????????")
				.setNegativeButton(string.ok_button, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						setResult(0, getIntent().putExtra("FilePath", mSelectedFilePath));
						finish();
					}
				})
				.setPositiveButton(string.cancel_button, null)
				.show();
			}
			else
				Toast.makeText(this, "?????? ?? ???? ????? ????.", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if( keyCode == KeyEvent.KEYCODE_BACK )
		{
			if( mSavePath.size() > 1 )
			{
				mSavePath.remove(mSavePath.size() - 1);
				String lastPath = mSavePath.get(mSavePath.size() - 1);
				getDir( lastPath );
				return true;
			}
			else
			{
				setResult(-1);
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}

