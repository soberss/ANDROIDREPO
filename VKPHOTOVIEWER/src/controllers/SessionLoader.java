package controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.example.vkphotoviewer.AlbumListActivity;

import models.CurrentSession;

import parsers.RegExpParser;
import utils.UrlStringBuilder;
import utils.VkSettings;



/** gets userId and accesstoken and loads its in object CurrentSession
 * when CurrentSession parameters has established call PhotoManager.startActivity */
public class SessionLoader extends WebViewClient{
	public static final String TAG = "SessionLoader";
	private final String PREF_USER_ID = "userId";	
	private final String PREF_ACCESS_TOKEN= "accessToken";
	private SharedPreferences mPref;
	private Context mContext;
	private static SessionLoader sInstance;
	private SessionLoader(){}	
	
	public static SessionLoader getInstance(){
		if(sInstance == null) sInstance = new SessionLoader(); 
		return sInstance;
	}
	
	public boolean loadSharedPreferences(){
		boolean res = true;
		Activity activity = (Activity) mContext;		
		mPref = activity.getPreferences(Context.MODE_PRIVATE);		
	    String userId = mPref.getString(PREF_USER_ID, "");
	    String accessToken = mPref.getString(PREF_ACCESS_TOKEN, "");
	    if(userId.length()==0 || accessToken.length()==0){
	    	res = false;
	    } else{
	    	setSession(userId,accessToken);
	    }
	    return res;		
	}
	
	public void saveSharedPreferences(String userId, String accessToken){		
		Activity activity = (Activity) mContext;		
		mPref = activity.getPreferences(Context.MODE_PRIVATE);				
	    Editor ed = mPref.edit();
	    ed.putString(PREF_USER_ID, userId);
	    ed.putString(PREF_ACCESS_TOKEN, accessToken);
	    ed.commit();			    	    	    		
	}
	
	public String buildAuthUrl() throws UnsupportedEncodingException{		
		String url="";		
		UrlStringBuilder sb = new UrlStringBuilder(VkSettings.BASE_AUTH_URL);		
		sb.addParam("client_id", CurrentSession.APP_ID);
		sb.addParam("display", "touch");
		sb.addParam("scope", CurrentSession.getInstance().getSettings());		
		sb.addParam("redirect_uri", URLEncoder.encode(VkSettings.REDIRECT_URL, "UTF-8"));
		sb.addParam("response_type", "token");
	    url = sb.buildUrl();			
		return url;
	}
	
	private void parseRedirectedUrl(String url){		               
        if(url.startsWith(VkSettings.REDIRECT_URL)) {
            if(!url.contains("error=")){            	
            	String accessToken=RegExpParser.extractPatternFromString(url, "access_token=(.*?)&");
                String userId=RegExpParser.extractPatternFromString(url, "user_id=(\\d*)");
                if(userId==null || userId.length()==0 || accessToken==null || accessToken.length()==0) return;
                setSession(userId,accessToken);
                saveSharedPreferences(userId, accessToken);
            }                            
        }
	}
	
	private void setSession(String userId, String accessToken){
		CurrentSession.getInstance().setAccessToken(accessToken);
		CurrentSession.getInstance().setUserId(userId);
	}
	
	public void resetSession(){
		setSession("", "");
		saveSharedPreferences("", "");
	}
	
	public void setContext(Context context){
		mContext = context;
	}
	
	/**when VK Auth Server send page with userId and access token in url,
	 * SessionLoader derives its parameters, sets Current Session and gets control to PhotoManager*/
	@Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);                    
            if(url==null) return;            
            if(url.startsWith(VkSettings.REDIRECT_URL)){            	            	
				parseRedirectedUrl(url);
				Intent intent=new Intent(); 
			    intent.setClass(mContext, AlbumListActivity.class);
			    mContext.startActivity(intent);        
			    Activity act = (Activity) mContext;
			    act.finish();									   				    				    			
            }                             
    }
	
	
}
