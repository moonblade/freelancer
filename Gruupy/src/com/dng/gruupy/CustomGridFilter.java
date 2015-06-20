package com.dng.gruupy;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class CustomGridFilter extends ArrayAdapter<ContactSec> {
	Context context;
	int layoutResourceId;
	//Creating ArrayList to Store Contact
	ArrayList<ContactSec> data = new ArrayList<ContactSec>();

	public CustomGridFilter(Context context, int layoutResourceId,
			ArrayList<ContactSec> gridArray) {
		super(context, layoutResourceId, gridArray);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = gridArray;
	}
	
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}
	
//Inflating items to be shown on ProfilePlaceholder
	
	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;
		DisplayImageOptions options = null;
		ImageLoader imageLoader = ImageLoader.getInstance();
		
		if (row == null) {
			holder = new RecordHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
					   getContext()).threadPoolSize(3)
					    .threadPriority(Thread.NORM_PRIORITY - 2)
					    .memoryCacheSize(1500000)
					    // 1.5 Mb
					    .denyCacheImageMultipleSizesInMemory()
					    .discCacheFileNameGenerator(new Md5FileNameGenerator())
					    .build();
					  ImageLoader.getInstance().init(config);
					  options = new DisplayImageOptions.Builder()
					    .showImageForEmptyUri(R.drawable.ic_launcher)
					    .cacheInMemory().cacheOnDisc()
					    .imageScaleType(ImageScaleType.EXACTLY)
					    .bitmapConfig(Bitmap.Config.RGB_565)
					    .displayer(new FadeInBitmapDisplayer(300)).build();
			row = inflater.inflate(layoutResourceId, parent, false);
		
			holder.name=(TextView)row.findViewById(R.id.text_name);
			holder.pic=(ImageView) row.findViewById(R.id.img_group);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}
		ContactSec cn = data.get(position);
		holder.name.setText(cn.getTitle());
			Uri uri = Uri.parse(cn.getPic());
			imageLoader.displayImage(uri.toString(), holder.pic, options);
		return row;
	}

	static class RecordHolder {
		TextView name;

		ImageView pic;
	}
}