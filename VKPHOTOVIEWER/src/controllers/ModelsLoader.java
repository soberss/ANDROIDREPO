package controllers;

import java.util.ArrayList;

import models.Album;
import models.CurrentSession;
import models.Photo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.UrlStringBuilder;
import utils.VkSettings;

import dataproviding.JsonDataProvider;

import android.os.Handler;
import android.os.Message;

public class ModelsLoader {
	private static ModelsLoader sInstance = null;	
	private ArrayList<Album> mAlbums = null;
	private Album mCurrentAlbum = null;
	private ModelsListAdapter mAlbumsListAdapter;
	private ModelsGridViewAdapter mAlbumPhotosListAdapter;	
	
	private ModelsLoader(){
		mAlbums = new ArrayList<Album>();
	}
	
	public static ModelsLoader getInstance(){
		if(sInstance == null) sInstance = new ModelsLoader();		
		return sInstance;
	}
	
	public void setAlbumsAdapter(ModelsListAdapter albumsListAdapter){
		mAlbumsListAdapter = albumsListAdapter;
	}
	
	public void setAlbumPhotosAdapter(ModelsGridViewAdapter albumPhotosListAdapter){
		mAlbumPhotosListAdapter = albumPhotosListAdapter;
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
	
	public void clearAlbumsList(){
		mCurrentAlbum.setCurrentPhoto(null);		
		mCurrentAlbum = null;
		mAlbums.clear();
		ImageLoader.getInstance().clearPhotosCache();
	}
	
	public void loadAlbumsList(){
		if(mAlbums.size() == 0){
			//load from url
			String url = createGetAlbumsUrl();
			loadAlbumsFromUrl(url);
		} else{
			//load from cache
		}
	}
	
	public void loadAlbumPhotosList(){
		if(mCurrentAlbum.getPhotos().size() == 0){
			String url = createGetAlbumPhotosUrl();
			loadAlbumPhotosFromUrl(url);
		}else{
			//load from cache
		}
	}
	
	private void loadAlbumsFromUrl(final String url){
		
		final Handler handler = new Handler() {			
            @Override
            public void handleMessage(Message message) {            	
            	JSONArray jsonArray = (JSONArray) message.obj;
            	for(int i=0; i < jsonArray.length(); i++){            									
        			try {				
        				JSONObject jObj = (JSONObject)jsonArray.get(i);
        				Album album = new Album(jObj);        				
        				mAlbums.add(album);		
        				mAlbumsListAdapter.notifyDataSetChanged();				
        			} catch (JSONException e) {				
        				e.printStackTrace();
        			}															                      	           
        		}
            }
        };
        getJsonResponse(url, handler);        							
	}
		
	private void loadAlbumPhotosFromUrl(final String url){
		
		final Handler handler = new Handler() {			
            @Override
            public void handleMessage(Message message) {            	
            	JSONArray jsonArray = (JSONArray) message.obj;
            	for(int i=0; i < jsonArray.length(); i++){            									
        			try {				
        				JSONObject jObj = (JSONObject)jsonArray.get(i);        				
        				Photo photo = new Photo(jObj);        				
        				mCurrentAlbum.addPhoto(photo);        				
        				mAlbumPhotosListAdapter.notifyDataSetChanged();
        			} catch (JSONException e) {				
        				e.printStackTrace();
        			}															                      	           
        		}
            }
        };        
        getJsonResponse(url, handler);        							
	}
	
	private void getJsonResponse(final String url, final Handler handler){
		new Thread() {			
            @Override
            public void run() {            	            	
            	JSONArray jsonArray;
        		JsonDataProvider dp = new JsonDataProvider(url);
        		dp.run();		
        		jsonArray = dp.getResult();
                Message message = handler.obtainMessage(0, jsonArray);
                handler.sendMessage(message);
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
}
