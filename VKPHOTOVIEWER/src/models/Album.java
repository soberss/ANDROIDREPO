package models;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

@SuppressWarnings("serial")
public class Album extends HashMap<String, Object> implements Model{
	
	private ArrayList<Photo> mPhotos = null;
	private Photo mCurrentPhoto = null;
	
	public Album(JSONObject jObj){
		super();		
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
	
	@Override
	public String getId(){
		return (String) get(ATTRIBUTE_NAME_ID);
	}
	
	@Override
	public String getTitle(){
		return (String) get(ATTRIBUTE_NAME_TITLE);
	}
	
	@Override
	public String getImageSrc(){
		return (String) get(ATTRIBUTE_NAME_IMAGE_SRC);
	}	
	
	@Override
	public void convertToObjectFromJson(JSONObject jObj){		
		put(ATTRIBUTE_NAME_TITLE, jObj.optString("title"));
		put(ATTRIBUTE_NAME_ID, jObj.optString("aid"));		
		put(ATTRIBUTE_NAME_IMAGE_SRC, jObj.optString("thumb_src"));			    	    
	}
}

