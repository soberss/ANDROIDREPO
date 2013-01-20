package com.example.vkphotoviewer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;

import java.io.UnsupportedEncodingException;

import com.example.vkphotoviewer.R;

import controllers.SessionLoader;

public class AuthActivity extends Activity {
	public static final String TAG = "AuthActivity";
	WebView wv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
                
        SessionLoader.getInstance().setContext(this);
        
        if(SessionLoader.getInstance().loadSharedPreferences()){
        	Intent intent=new Intent(); 
	        intent.setClass(this, AlbumListActivity.class);
	        startActivity(intent);        	
        }else{
        	wv = (WebView) findViewById(R.id.vkview);
            adjustWebView();
            String url="";        
    		try {
    			url = SessionLoader.getInstance().buildAuthUrl();		
    			wv.loadUrl(url);
    		} catch (UnsupportedEncodingException e) {    			
    			Log.e(TAG, e.getMessage());
    		}
        }                       					                									 						                                              
    }

    public void adjustWebView(){
    	wv.getSettings().setJavaScriptEnabled(true);
        wv.clearCache(true);                
        wv.setWebViewClient(SessionLoader.getInstance());                        
        CookieSyncManager.createInstance(this);        
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.activity_auth, menu);
        return true;
    }
    
}
