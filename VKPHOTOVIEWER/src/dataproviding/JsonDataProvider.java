package dataproviding;

import org.json.JSONArray;

import parsers.JsonParser;

import java.io.IOException;
import java.io.InputStream;


/**gets JSON with async loading from url to its inner field to be requested*/
public class JsonDataProvider extends UrlDataProvider {
	private final static String TAG = "JsonDataProvider";
	private JSONArray mJsonArray;	
	public JsonDataProvider(String url){
		super(url);		
	}
	
	public JSONArray getResult(){
		return mJsonArray;
	}
	
	@Override
	public void run(){		
		try {
			JSONArray jArray = null;
			InputStream is;
			is = createInputStreamFromUrl();
			JsonParser parser = new JsonParser(is);
			parser.execute();
			jArray = parser.getResult();
			mJsonArray = jArray; 
		} catch (IOException e) {			
			e.printStackTrace();
		}
					    
	}			

}
