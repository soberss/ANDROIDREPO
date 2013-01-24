package com.example.vkphotoviewer.controllers;

import com.example.vkphotoviewer.R;
import com.example.vkphotoviewer.controllers.ImageLoader;
import com.example.vkphotoviewer.controllers.ModelsLoader;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class ItemPagerAdapter extends PagerAdapter{

	private LayoutInflater inflater;
	
	public ItemPagerAdapter(Activity context){
		inflater = context.getLayoutInflater();
	}
	
	@Override
	public int getCount() {
		return  ModelsLoader.getInstance().getCurrentAlbum().getPhotos().size();
		
	}

	@Override
	public void destroyItem(View container, int position, Object object) {		
		View rootView = (View) object;		
		((ViewPager) container).removeView(rootView);
	}
	
	@Override
	public Object instantiateItem(View view, int position) {		
		Bitmap bitmap;
		final View imageLayout = inflater.inflate(R.layout.image_pager_item, null);
		final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.itemImage);		
		 		
		ImageLoader.ResultHandler hr = new ImageLoader.ResultHandler(){
			@Override			
			public void loadingComplete(String cachedUrl) {
				imageView.setImageBitmap(ImageLoader.getInstance().getCachedBitmapByUrl(cachedUrl));			
			}};
			
		String url = ModelsLoader.getInstance().getCurrentAlbum().getPhotos().get(position).getImageBigSrc();
		bitmap = ImageLoader.getInstance().loadImage(url, hr);
		imageView.setImageBitmap(bitmap);				
		
		((ViewPager) view).addView(imageLayout, 0);
		return imageLayout;
	}

	@Override
	public void finishUpdate(View container) {
	}
	
	@Override
	public boolean isViewFromObject(View view, Object object) {		
		return view.equals(object);		
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View container) {
	}
		

}
