package com.example.vkphotoviewer.models;

import java.util.ArrayList;

import org.json.JSONObject;

public class Album {
	private String mId;
	private String mTitle;
	private String mImageSrc;
	
	private ArrayList<Photo> mPhotos = null;
	private Photo mCurrentPhoto = null;
	
	public Album(JSONObject jObj){			
		convertToObjectFromJson(jObj);
		mPhotos = new ArrayList<Photo>();
	}
	
	public void addPhoto(Photo photo){		
		mPhotos.add(photo);
	}
	
	public void setCurrentPhoto(Photo photo){
		mCurrentPhoto = photo;
	}
	
	public Photo getCurrentPhoto(){
		return mCurrentPhoto;
	}
	
	public  ArrayList<Photo> getPhotos(){
		return mPhotos;
	}
		
	public String getId(){
		return mId;
	}
		
	public String getTitle(){
		return mTitle;
	}
		
	public String getImageSrc(){
		return mImageSrc;
	}	
		
	public void convertToObjectFromJson(JSONObject jObj){
		mTitle = jObj.optString("title");
		mId = jObj.optString("aid");
		mImageSrc = jObj.optString("thumb_src");					    	    
	}
}

