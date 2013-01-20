package com.example.vkphotoviewer;

import com.example.vkphotoviewer.R;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

/**
 * An activity representing a single Album detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link AlbumListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link AlbumDetailFragment}.
 */
public class AlbumDetailActivity extends FragmentActivity implements AlbumDetailGridFragment.AlbumPhotosCallbacks {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_album_detail);
		
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
				
		if (savedInstanceState == null) {			
			Bundle arguments = new Bundle();
			arguments.putString(AlbumDetailGridFragment.ALBUM_ID, getIntent().getStringExtra(AlbumDetailGridFragment.ALBUM_ID));
			AlbumDetailGridFragment fragment = new AlbumDetailGridFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction().add(R.id.album_detail_container, fragment).commit();
		}
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:			
			NavUtils.navigateUpTo(this, new Intent(this, AlbumListActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onPhotoSelected(int position) {
		DialogFragment photoDialog = new PhotoPagerDialog(position);
		photoDialog.show(getSupportFragmentManager(), "fullscreen_photo");		
	}
}
