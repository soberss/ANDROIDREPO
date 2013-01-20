package com.example.vkphotoviewer;

import com.example.vkphotoviewer.R;

import models.Album;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import controllers.ModelsListAdapter;
import controllers.ModelsLoader;


public class AlbumListFragment extends ListFragment {
	
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	
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
		ModelsListAdapter albumsAdapter = new ModelsListAdapter(getActivity(), ModelsLoader.getInstance().getAlbums(), 
				R.layout.image_list_item, ModelsListAdapter.LIST_ATTR_NAMES, ModelsListAdapter.LIST_VIEWS);
		setListAdapter(albumsAdapter);
		ModelsLoader.getInstance().setAlbumsAdapter(albumsAdapter);
		ModelsLoader.getInstance().loadAlbumsList();
		
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);	
		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
			setActivatedPosition(savedInstanceState
					.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof AlbumsCallbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (AlbumsCallbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sEmptyCallbacks;
	}

	@Override
	public void onListItemClick(ListView listView, View view, int position,
			long id) {
		super.onListItemClick(listView, view, position, id);
		Album album = (Album) listView.getItemAtPosition(position);
		ModelsLoader.getInstance().setCurrentAlbum(album);
		// Notify the active callbacks interface (the activity, if the
		// fragment is attached to one) that an item has been selected.
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
