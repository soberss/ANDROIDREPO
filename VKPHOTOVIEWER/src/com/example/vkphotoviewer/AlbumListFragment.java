package com.example.vkphotoviewer;

import com.example.vkphotoviewer.controllers.ItemListAdapter;
import com.example.vkphotoviewer.controllers.ModelsLoader;
import com.example.vkphotoviewer.models.Album;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;



public class AlbumListFragment extends ListFragment {
	
	private static final String STATE_ACTIVATED_POSITION = "activated_position";
	private ItemListAdapter mAlbumsAdapter;
	
	private AlbumsCallbacks mCallbacks = sEmptyCallbacks;
	
	private int mActivatedPosition = ListView.INVALID_POSITION;

	
	public interface AlbumsCallbacks {		
		public void onAlbumSelected(String id);
	}

	
	private static AlbumsCallbacks sEmptyCallbacks = new AlbumsCallbacks() {
		@Override
		public void onAlbumSelected(String id) {
		}
	};

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public AlbumListFragment() {}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		mAlbumsAdapter = new ItemListAdapter(getActivity(), ModelsLoader.getInstance().getAlbums());
		setListAdapter(mAlbumsAdapter);
				
		ModelsLoader.getInstance().loadAlbumsList(new ModelsLoader.AdapterCallbacks(){
			@Override
			public void onModelAdded() {
				mAlbumsAdapter.notifyDataSetChanged();				
			}});
		
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);			
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		if (!(activity instanceof AlbumsCallbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}
		mCallbacks = (AlbumsCallbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = sEmptyCallbacks;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
		Album album = (Album) listView.getItemAtPosition(position);
		ModelsLoader.getInstance().setCurrentAlbum(album);		
		mCallbacks.onAlbumSelected(album.getId());
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		getListView().setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}

	private void setActivatedPosition(int position) {
		if (position == ListView.INVALID_POSITION) {
			getListView().setItemChecked(mActivatedPosition, false);
		} else {
			getListView().setItemChecked(position, true);
		}

		mActivatedPosition = position;
	}
	
}
