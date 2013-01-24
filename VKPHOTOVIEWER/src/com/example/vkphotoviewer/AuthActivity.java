package com.example.vkphotoviewer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


import com.example.vkphotoviewer.R;
import com.example.vkphotoviewer.controllers.SessionLoader;
import com.example.vkphotoviewer.models.CurrentSession;
import com.example.vkphotoviewer.parsers.RegExpParser;
import com.example.vkphotoviewer.utils.UrlStringBuilder;
import com.example.vkphotoviewer.utils.VkSettings;


public class AuthActivity extends Activity {
	public static final String TAG = "AuthActivity";
	public static final String PREF_USER_ID = "userId";	
	public static final String PREF_ACCESS_TOKEN = "accessToken";
	public static final String PREF_FILE_NAME = "VkPrefs";
	
	private SharedPreferences mPref;	
	private WebView wv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        final Activity activity = this;
                                
        WebViewCallbacks callBacks = new WebViewCallbacks(){
			@Override
			public void onSetSession(String[] sessionParams) {
				saveSharedPreferences(sessionParams[0], sessionParams[1]);				
				Intent intent=new Intent();							    
				intent.setClass(activity, AlbumListActivity.class);
				activity.startActivity(intent);				
			}
		};
                
        if(loadSharedPreferences()){
        	Intent intent=new Intent(); 
	        intent.setClass(this, AlbumListActivity.class);
	        startActivity(intent);        	
        }else{
        	SessionLoader sessionLoader = new SessionLoader(callBacks); 
        	wv = (WebView) findViewById(R.id.vkview);
        	
        	wv.getSettings().setJavaScriptEnabled(true);
            wv.clearCache(true);                
            wv.setWebViewClient(sessionLoader);                        
            CookieSyncManager.createInstance(this);        
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            
            String url="";        
    		try {
    			url = sessionLoader.buildAuthUrl();		
    			wv.loadUrl(url);
    		} catch (UnsupportedEncodingException e) {    			
    			Log.e(TAG, e.getMessage());
    		}
        }                       					                									 						                                              
    }   
    
    
    private boolean loadSharedPreferences(){
		boolean res = true;		
		mPref = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
	    String userId = mPref.getString(PREF_USER_ID, "");
	    String accessToken = mPref.getString(PREF_ACCESS_TOKEN, "");
	    if(userId.length()==0 || accessToken.length()==0){
	    	res = false;
	    } else{	    	
	    	CurrentSession.getInstance().setUserId(userId);
	    	CurrentSession.getInstance().setAccessToken(accessToken);
	    }
	    return res;		
	}
	
	private void saveSharedPreferences(String userId, String accessToken){						
		mPref = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);		
	    Editor ed = mPref.edit();	    
	    ed.putString(PREF_USER_ID, userId);
	    ed.putString(PREF_ACCESS_TOKEN, accessToken);
	    ed.commit();			    	    	    		
	}
        
    public interface WebViewCallbacks{
    	public void onSetSession(String[] sessionParams);
    }
    
}
