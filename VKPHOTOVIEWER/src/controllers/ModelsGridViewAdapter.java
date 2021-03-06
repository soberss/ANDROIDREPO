package controllers;

import java.util.List;
import java.util.Map;

import com.example.vkphotoviewer.R;

import models.ListItemView;
import models.Model;

import controllers.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ModelsGridViewAdapter extends SimpleAdapter{
	
	private Activity mActivity;
	
	public ModelsGridViewAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);		
		mActivity = (Activity) context;		
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		Bitmap bitmap;
        LayoutInflater inflater = mActivity.getLayoutInflater();
        View itemView = convertView;
        ListItemView cachedItemView;
        if (itemView == null) {            
            itemView = inflater.inflate(R.layout.image_grid_item, null);
            cachedItemView = new ListItemView(itemView);
            itemView.setTag(cachedItemView);
        } else {
            cachedItemView = (ListItemView) itemView.getTag();
        }
        
        Model model = (Model) getItem(position);
        
        String imageUrl = model.getImageSrc();
        final ImageView imageView = cachedItemView.getImageView();
        imageView.setTag(imageUrl);
                
        ImageLoader.ResultHandler hr = new ImageLoader.ResultHandler(){
			@Override
			public void loadingComplete(Bitmap bitmap, String url) {
				imageView.setImageBitmap(bitmap);				
			}};
			
		bitmap = ImageLoader.getInstance().loadImage(imageUrl, hr);
		imageView.setImageBitmap(bitmap);	
					                        
        TextView textView = cachedItemView.getTitleTextView();
        textView.setText(model.getTitle());
        
        return itemView;
    }
}
