package com.example.vkphotoviewer.models;

import com.example.vkphotoviewer.R;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ListItemView {

	private View mParentView;
    private TextView mTextView;
    private ImageView mImageView;
    
    public ListItemView(View view){
    	mParentView = view;
    }
    
    public TextView getTitleTextView() {
        if (mTextView == null) {
        	mTextView = (TextView) mParentView.findViewById(R.id.itemTitle);
        }
        return mTextView;
    }
 
    public ImageView getImageView() {
        if (mImageView == null) {
            mImageView = (ImageView) mParentView.findViewById(R.id.itemImage);
        }
        return mImageView;
    }
}
