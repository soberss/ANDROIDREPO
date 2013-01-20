package parsers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**gets InputSream and convert it to JSONarray in its inner field*/
public class JsonParser{
	private final static String TAG = "JSONParser";
	private InputStream mIs;
	private JSONArray mArray;		
	
	public JsonParser(InputStream is){		
		mIs = is;
	}
			
	public JSONArray getResult(){
		return mArray;
	}
	public void execute(){
		Log.v(TAG, "execute...");
		BufferedReader  in = null;							
		in = new BufferedReader(new InputStreamReader(mIs));
		StringBuffer sb = new StringBuffer("");
		String line = "";					
		
		try {
			while((line = in.readLine())!= null){
				sb.append(line);
			}
			in.close();
			mIs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		String jsonString = sb.toString();
		JSONObject root;
		try {
			root = new JSONObject(jsonString);
			mArray=root.optJSONArray("response");
			Log.v(TAG, "getting json array...");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
}
