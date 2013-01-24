package com.example.vkphotoviewer.dataproviding;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**provides methods to execute http requests and gets inputstream*/
public abstract class UrlDataProvider{
	private final static String TAG = "URLDataProvider";
	protected String mUrl;	
	public UrlDataProvider(String url){
		mUrl = url;
	}
	protected InputStream createInputStreamFromUrl() throws IOException {		
		URL url = new URL(mUrl);		
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        
        conn.connect();
        int response = conn.getResponseCode();
        Log.d(TAG, "The response is: " + response);
        return  conn.getInputStream();						
	}
		
	public abstract Object getResult();
	public abstract void run() throws InterruptedException, ExecutionException;
}
