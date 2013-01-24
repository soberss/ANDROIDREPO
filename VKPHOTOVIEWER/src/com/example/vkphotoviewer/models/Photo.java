package com.example.vkphotoviewer.models;

import org.json.JSONObject;

public class Photo {
	
	private String mId;
	private String mParentId;
	private String mTitle;
	private String mImageSrc;
	private String mImageBigSrc;
	
	public Photo(JSONObject jObj){		
		convertToObjectFromJson(jObj);		
	}
		
	public String getId(){
		return mId;
	}
	
	public String getParentId(){
		return  mParentId;
	}
		
	public String getTitle(){
		return mTitle;
	}
		
	public String getImageSrc(){
		return mImageSrc;
	}
	
	public String getImageBigSrc(){
		return mImageBigSrc;
	}	
		
	public void convertToObjectFromJson(JSONObject jObj) {
		mId = jObj.optString("pid");
		mParentId = jObj.optString("aid");
		mTitle = jObj.optString("text");
		mImageSrc = jObj.optString("src");
		mImageBigSrc = jObj.optString("src_big");				
	}

}
