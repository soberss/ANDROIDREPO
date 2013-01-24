package com.example.vkphotoviewer.controllers;

import java.util.ArrayList;


import com.example.vkphotoviewer.R;
import com.example.vkphotoviewer.models.Album;
import com.example.vkphotoviewer.models.ListItemView;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemListAdapter extends BaseAdapter {
	private FragmentActivity mActivity;
	private ArrayList<Album> mAlbums;
	
	public ItemListAdapter(FragmentActivity activity, ArrayList<Album> albums){
		mActivity = activity;
		mAlbums = albums;
	}
	
	@Override
	public int getCount() {		
		return mAlbums.size();
	}
	
	@Override
	public Object getItem(int position) {		
		return mAlbums.get(position);
	}

	@Override
	public long getItemId(int position) {		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Bitmap bitmap;        
        View itemView = convertView;
        ListItemView cachedItemView;
        
        if (itemView == null) {
        	LayoutInflater inflater = mActivity.getLayoutInflater();
            itemView = inflater.inflate(R.layout.image_list_item, null);            
            cachedItemView = new ListItemView(itemView);
            itemView.setTag(cachedItemView);
        } else {
            cachedItemView = (ListItemView) itemView.getTag();
        }
        
        Album model = (Album) getItem(position);
        
        String imageUrl = model.getImageSrc();
        final ImageView imageView = cachedItemView.getImageView();                
        
        ImageLoader.ResultHandler hr = new ImageLoader.ResultHandler(){        	
			@Override						
			public void loadingComplete(String cachedUrl) {
				imageView.setImageBitmap(ImageLoader.getInstance().getCachedBitmapByUrl(cachedUrl));				
			}};
			
		bitmap = ImageLoader.getInstance().loadImage(imageUrl, hr);
		imageView.setImageBitmap(bitmap);	
					                        
        TextView textView = cachedItemView.getTitleTextView();
        textView.setText(model.getTitle());
        
        return itemView;		
	}

}
