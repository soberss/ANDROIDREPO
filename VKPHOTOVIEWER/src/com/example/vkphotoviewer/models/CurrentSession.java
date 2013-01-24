package com.example.vkphotoviewer.models;


/**represents parameters of user session form VK API calling*/
public class CurrentSession {
	public static final String APP_ID = "3122403";		
	private String mAccessToken;
	private String mUserId;
	private int mSettings;	
	private static CurrentSession sInstance;
	
	private CurrentSession(){		
		this.mSettings=4;
	}
	
	public static CurrentSession getInstance(){		
		if(sInstance == null) sInstance = new CurrentSession(); 
		return sInstance;
	}
	
	public String getAccessToken(){
		return this.mAccessToken;
	}
	
	public void setAccessToken(String accessToken){
		this.mAccessToken = accessToken;
	}
	
	public String getUserId(){
		return this.mUserId;
	}
	
	public void setUserId(String userId){
		this.mUserId = userId;
	}
	
	public String getSettings(){		
		return String.valueOf(this.mSettings);
	}
			
}
