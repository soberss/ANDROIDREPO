package models;

import android.graphics.Bitmap;

import org.json.JSONObject;

/**represents data of PhotoModel and AlbumModel to be loaded by ThumLoader and then rendered in Activity by Renderer*/
public interface Model {
	
	public String ATTRIBUTE_NAME_TITLE = "title";
	public String ATTRIBUTE_NAME_ID = "id";
	public String ATTRIBUTE_NAME_PARENT_ID = "parent_id";
	public String ATTRIBUTE_NAME_IMAGE_SRC = "image_src";
	public String ATTRIBUTE_NAME_IMAGE_BIG_SRC = "image_big_src";
	//public String ATTRIBUTE_NAME_IMAGE_BITMAP = "image_bitmap";
	
	public void convertToObjectFromJson(JSONObject o);
	public String getId();
	public String getTitle();
	public String getImageSrc();
	//public void setImageBitmap(Bitmap bitmap);						
}
