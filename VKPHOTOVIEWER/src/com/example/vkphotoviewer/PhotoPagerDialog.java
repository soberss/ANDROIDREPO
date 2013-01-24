package com.example.vkphotoviewer;

import com.example.vkphotoviewer.R;
import com.example.vkphotoviewer.controllers.ItemPagerAdapter;
import com.example.vkphotoviewer.controllers.ModelsLoader;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;


public class PhotoPagerDialog extends DialogFragment implements OnClickListener{
	private int mPosition;
	
	public PhotoPagerDialog(){}
	
	public PhotoPagerDialog(int position){
		mPosition = position;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	 	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
						
	    getDialog().setTitle(ModelsLoader.getInstance().getCurrentAlbum().getTitle());
	    View v = inflater.inflate(R.layout.dialog_pager_fragment, null);
	    	    	    	    
	    ViewPager pager = (ViewPager) v.findViewById(R.id.pager);	    
	    pager.setAdapter(new ItemPagerAdapter(getActivity()));	    
	    pager.setCurrentItem(mPosition);
	    		    		    
	    return v;
	  }

	
	@Override
	public void onClick(View v) {		
		dismiss();
	}

	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);		
	}
}
