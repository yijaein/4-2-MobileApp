package yi.jaein.musicplayer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore.Images;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondTabAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private int mLayout;
	private ArrayList<SecondTabItem> mItems;

	SecondTabAdapter(Context context, int layout, ArrayList<SecondTabItem> item) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mLayout = layout;
		mItems = item;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mItems.get(position);
	}

	@Override
	public long getItemId(int resourceID) {
		// TODO Auto-generated method stub
		return resourceID;
	}

	@Override
	public View getView(int position, View oldView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if( oldView == null )
		{
			oldView = mInflater.inflate(mLayout, parent, false);
		}
		ImageView imageView = (ImageView) oldView.findViewById(R.id.listImage);
		TextView titleView = (TextView) oldView.findViewById(R.id.listPlayTitle);
		TextView contentView = (TextView) oldView.findViewById(R.id.listContent);
		Bitmap bm = null;
		try {
			bm = Images.Media.getBitmap(oldView.getContext().getContentResolver(), mItems.get(position).GetImageUri());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if( bm != null )
			imageView.setImageBitmap(bm);
		titleView.setText(mItems.get(position).GetTitle());
		contentView.setText(mItems.get(position).GetContent());
		return oldView;
	}
}
