package models;

import java.util.HashMap;

import org.json.JSONObject;

import android.graphics.Bitmap;

@SuppressWarnings("serial")
public class Photo extends HashMap<String, Object> implements Model{

	public Photo(JSONObject jObj){
		super();
		convertToObjectFromJson(jObj);		
	}
	
	@Override
	public String getId(){
		return (String) get(ATTRIBUTE_NAME_ID);
	}
	
	public String getParentId(){
		return (String) get(ATTRIBUTE_NAME_PARENT_ID);
	}
	
	@Override
	public String getTitle(){
		return (String) get(ATTRIBUTE_NAME_TITLE);
	}
	
	@Override
	public String getImageSrc(){
		return (String) get(ATTRIBUTE_NAME_IMAGE_SRC);
	}
	
	public String getImageBigSrc(){
		return (String) get(ATTRIBUTE_NAME_IMAGE_BIG_SRC);
	}	
	
	@Override
	public void convertToObjectFromJson(JSONObject jObj) {
		put(ATTRIBUTE_NAME_ID, jObj.optString("pid"));		
		put(ATTRIBUTE_NAME_PARENT_ID, jObj.optString("aid"));
		put(ATTRIBUTE_NAME_TITLE, jObj.optString("text"));				
		put(ATTRIBUTE_NAME_IMAGE_SRC, jObj.optString("src"));
		put(ATTRIBUTE_NAME_IMAGE_BIG_SRC, jObj.optString("src_big"));				
	}

}
