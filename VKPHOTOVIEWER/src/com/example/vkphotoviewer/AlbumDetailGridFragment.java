package com.example.vkphotoviewer;

import com.example.vkphotoviewer.R;
import com.example.vkphotoviewer.controllers.ItemGridAdapter;
import com.example.vkphotoviewer.controllers.ModelsLoader;
import com.example.vkphotoviewer.models.Photo;



import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

public class AlbumDetailGridFragment extends Fragment {
		
	private ItemGridAdapter mAlbumPhotosAdapter;
	private GridView mGridView;
	private TextView mTitle;
	
	public static final String ALBUM_ID = "item_id";
	private AlbumPhotosCallbacks mCallbacks = sEmptyCallbacks;
	
	public interface AlbumPhotosCallbacks {		
		public void onPhotoSelected(int position);
	}
	
	private static AlbumPhotosCallbacks sEmptyCallbacks = new AlbumPhotosCallbacks() {
		@Override
		public void onPhotoSelected(int  position) {
		}
	};
	
	public AlbumDetailGridFragment(){}
			
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments().containsKey(ALBUM_ID)) {}		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = null;
		
		rootView = inflater.inflate(R.layout.fragment_album_detail, container, false);
			
		mTitle = (TextView) rootView.findViewById(R.id.tvTitle);
		mTitle.setText(ModelsLoader.getInstance().getCurrentAlbum().getTitle());		
			
		mGridView = (GridView) rootView.findViewById(R.id.gridview);		
		mGridView.setOnItemClickListener(gvOnItemClickListener);
		
		//ItemGridAdapter 
		mAlbumPhotosAdapter = new ItemGridAdapter(getActivity(), ModelsLoader.getInstance().getCurrentAlbum().getPhotos());
		mGridView.setAdapter(mAlbumPhotosAdapter);
		//ModelsLoader.getInstance().setAlbumPhotosAdapter(albumPhotosAdapter);
		ModelsLoader.getInstance().loadAlbumPhotosList(new ModelsLoader.AdapterCallbacks(){						
			@Override
			public void onModelAdded() {				
				mAlbumPhotosAdapter.notifyDataSetChanged();
			}
		});
					
		return rootView;
	}
	
	private GridView.OnItemClickListener gvOnItemClickListener = new GridView.OnItemClickListener() {		
		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {			
			Photo photo = (Photo) mGridView.getItemAtPosition(position);
			ModelsLoader.getInstance().getCurrentAlbum().setCurrentPhoto(photo);					
			mCallbacks.onPhotoSelected(position);
		}
	};
			
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);		
		if (!(activity instanceof AlbumPhotosCallbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}
		mCallbacks = (AlbumPhotosCallbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();		
		mCallbacks = sEmptyCallbacks;
	}
}
