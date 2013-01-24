package com.example.vkphotoviewer.controllers;

import java.util.ArrayList;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.vkphotoviewer.dataproviding.JsonDataProvider;
import com.example.vkphotoviewer.models.Album;
import com.example.vkphotoviewer.models.CurrentSession;
import com.example.vkphotoviewer.models.Photo;
import com.example.vkphotoviewer.utils.UrlStringBuilder;
import com.example.vkphotoviewer.utils.VkSettings;



import android.os.Handler;
import android.os.Message;

public class ModelsLoader {
	private static ModelsLoader sInstance = null;	
	private ArrayList<Album> mAlbums = null;
	private Album mCurrentAlbum = null;			
	
	private ModelsLoader(){
		mAlbums = new ArrayList<Album>();
	}
	
	public static ModelsLoader getInstance(){
		if(sInstance == null) sInstance = new ModelsLoader();		
		return sInstance;
	}	
					
	public void setCurrentAlbum(Album album){
		mCurrentAlbum = album;
	}
	
	public Album getCurrentAlbum(){
		return mCurrentAlbum;
	}
	
	public ArrayList<Album> getAlbums(){					
		return mAlbums;
	}	
	
	public void loadAlbumsList(AdapterCallbacks listCallbacks){
		if(mAlbums.size() == 0){
			//load from url
			String url = createGetAlbumsUrl();
			loadAlbumsFromUrl(url, listCallbacks);
		} else{
			//load from cache
		}
	}
	
	public void loadAlbumPhotosList(AdapterCallbacks gridCallbacks){
		if(mCurrentAlbum.getPhotos().size() == 0){
			String url = createGetAlbumPhotosUrl();
			loadAlbumPhotosFromUrl(url, gridCallbacks);
		}else{
			//load from cache
		}
	}
	
	private void loadAlbumsFromUrl(final String url, final AdapterCallbacks listCallbacks){
				
		final Handler handler = new Handler() {			
            @Override
            public void handleMessage(Message message) {            	            	
            	listCallbacks.onModelAdded();
            }
        };        
		
        new Thread() {			
            @Override
            public void run() {            	            	
            	JSONArray jsonArray;
        		JsonDataProvider dp = new JsonDataProvider(url);
        		dp.run();		
        		jsonArray = dp.getResult();
        		for(int i=0; i < jsonArray.length(); i++){            									
        			try {				
        				JSONObject jObj = (JSONObject)jsonArray.get(i);        				
        				Album album = new Album(jObj);        				
        				mAlbums.add(album);        				
        				Message message = handler.obtainMessage();
                        handler.sendMessage(message);        				
        			} catch (JSONException e) {				
        				e.printStackTrace();
        			}															                      	           
        		}
            }
        }.start();	
	}
		
	private void loadAlbumPhotosFromUrl(final String url, final AdapterCallbacks gridCallbacks){
		
		final Handler handler = new Handler() {			
            @Override
            public void handleMessage(Message message) {            	            	            	
            	gridCallbacks.onModelAdded();
            }
        };        
		
        new Thread() {			
            @Override
            public void run() {            	            	
            	JSONArray jsonArray;
        		JsonDataProvider dp = new JsonDataProvider(url);
        		dp.run();		
        		jsonArray = dp.getResult();
        		for(int i=0; i < jsonArray.length(); i++){            									
        			try {				
        				JSONObject jObj = (JSONObject)jsonArray.get(i);        				
        				Photo photo = new Photo(jObj);        				
        				mCurrentAlbum.addPhoto(photo);         				
        				Message message = handler.obtainMessage();
                        handler.sendMessage(message);        				
        			} catch (JSONException e) {				
        				e.printStackTrace();
        			}															                      	           
        		}
            }
        }.start();	
	}
		
	
	private String createGetAlbumsUrl(){
		String url="";		
		UrlStringBuilder sb = new UrlStringBuilder(VkSettings.BASE_URL, VkSettings.M_GET_ALBUMS);
		sb.addParam("uid", CurrentSession.getInstance().getUserId());
		sb.addParam("need_covers", "1");
		sb.addParam("access_token", CurrentSession.getInstance().getAccessToken());				
		url = sb.buildUrl();		
		return url;
	}
	
	private String createGetAlbumPhotosUrl(){
		String url="";		
		UrlStringBuilder sb = new UrlStringBuilder(VkSettings.BASE_URL, VkSettings.M_GET_ALBUM_PHOTOS);
		sb.addParam("uid", CurrentSession.getInstance().getUserId());
		sb.addParam("aid", mCurrentAlbum.getId());
		sb.addParam("access_token", CurrentSession.getInstance().getAccessToken());				
		url = sb.buildUrl();		
		return url;
	}
	
	public void clearAlbumsList(){
		mCurrentAlbum.setCurrentPhoto(null);		
		mCurrentAlbum = null;
		mAlbums.clear();
		ImageLoader.getInstance().clearPhotosCache();
	}
	
	public interface AdapterCallbacks{
		public void onModelAdded();
	};
}
