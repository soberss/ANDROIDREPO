package com.example.vkphotoviewer.controllers;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.example.vkphotoviewer.AuthActivity.WebViewCallbacks;
import com.example.vkphotoviewer.models.CurrentSession;
import com.example.vkphotoviewer.parsers.RegExpParser;
import com.example.vkphotoviewer.utils.UrlStringBuilder;
import com.example.vkphotoviewer.utils.VkSettings;





/** gets userId and accesstoken and loads its in object CurrentSession
 * when CurrentSession parameters has established call PhotoManager.startActivity */
public class SessionLoader extends WebViewClient{
	public static final String TAG = "SessionLoader";
	
	WebViewCallbacks mCallBacks;
	public SessionLoader(WebViewCallbacks callBacks){
		mCallBacks = callBacks;
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
	
	private String[] parseRedirectedUrl(String url){
		String accessToken = null;
		String userId = null;;
        if(url.startsWith(VkSettings.REDIRECT_URL)) {
            if(!url.contains("error=")){            	
            	accessToken=RegExpParser.extractPatternFromString(url, "access_token=(.*?)&");
                userId=RegExpParser.extractPatternFromString(url, "user_id=(\\d*)");
                if(userId==null || userId.length()==0 || accessToken==null || accessToken.length()==0) return null;
                setSession(userId,accessToken);                
            }                            
        }
        return new String[]{userId, accessToken};
	}
	
	private void setSession(String userId, String accessToken){
		CurrentSession.getInstance().setAccessToken(accessToken);
		CurrentSession.getInstance().setUserId(userId);
	}	
	
	/**when VK Auth Server send page with userId and access token in url,
	 * SessionLoader derives its parameters, sets Current Session and gets control to PhotoManager*/
	@Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);                    
            if(url==null) return;            
            if(url.startsWith(VkSettings.REDIRECT_URL)){            	            	
				String[] res = parseRedirectedUrl(url);
				if(res != null){
					mCallBacks.onSetSession(res);
				}												   				    				    		
            }                             
    }		
}
