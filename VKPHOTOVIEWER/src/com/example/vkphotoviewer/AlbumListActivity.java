package com.example.vkphotoviewer;

import com.example.vkphotoviewer.R;
import com.example.vkphotoviewer.controllers.ModelsLoader;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class AlbumListActivity extends FragmentActivity implements
		AlbumListFragment.AlbumsCallbacks, AlbumDetailGridFragment.AlbumPhotosCallbacks {
	
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_album_list);

		if (findViewById(R.id.album_detail_container) != null) {			
			mTwoPane = true;			
			((AlbumListFragment) getSupportFragmentManager().findFragmentById(R.id.album_list)).setActivateOnItemClick(true);
		}	
		
	}
	
	@Override
	public void onAlbumSelected(String id) {
		if (mTwoPane) {			
			Bundle arguments = new Bundle();
			arguments.putString(AlbumDetailGridFragment.ALBUM_ID, id);			
			AlbumDetailGridFragment fragment = new AlbumDetailGridFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction().replace(R.id.album_detail_container, fragment).commit();

		} else {			
			Intent detailIntent = new Intent(this, AlbumDetailActivity.class);
			detailIntent.putExtra(AlbumDetailGridFragment.ALBUM_ID, id);
			startActivity(detailIntent);
		}
	}

	@Override
	public void onPhotoSelected(int position) {		
		DialogFragment photoDialog = new PhotoPagerDialog(position);
		photoDialog.show(getSupportFragmentManager(), "fullscreen_photo");				
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_album_list, menu);	    
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
	    switch (item.getItemId()) {	        
	        case R.id.menu_logout:
	        		        	
	        	SharedPreferences mPref = getSharedPreferences(AuthActivity.PREF_FILE_NAME, Context.MODE_PRIVATE);		
	    	    Editor ed = mPref.edit();	    	    
	    	    ed.clear();
	        	ed.commit();
	        	
	        	intent = new Intent(); 
		        intent.setClass(this, AuthActivity.class);
		        this.startActivity(intent);        
		        Activity curActivity = (Activity) this;
		        curActivity.finish();	        		            
	            return true;
	        case R.id.menu_clear:	        		        	
	        	ModelsLoader.getInstance().clearAlbumsList();
	        	intent = new Intent(); 
		        intent.setClass(this, AlbumListActivity.class);
		        this.startActivity(intent);
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);	            
	    }	    
	}
	
}
