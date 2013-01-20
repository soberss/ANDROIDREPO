package utils;

public class UrlStringBuilder {	
	private String mBaseUrl;
	private String mMethodName;
	private String mParamsUrlPart;		
	
	public UrlStringBuilder(String baseUrl, String methodName){
		this.mBaseUrl = baseUrl;
		this.mMethodName = methodName;				
		this.mParamsUrlPart = "";
	}

	public UrlStringBuilder(String baseUrl){
		this.mBaseUrl = baseUrl;		
		this.mParamsUrlPart = "";			
	}
	
	public void addParam(String param_name, String param_value){
		if(param_value==null || param_value.length()==0) return;                
        if(this.mParamsUrlPart.length()==0){
        	mParamsUrlPart += param_name + "=" + param_value;
        } else{
        	mParamsUrlPart += "&" + param_name + "=" + param_value;
        }        
	}		
	
	public String buildUrl(){
		String url="";				
		if(this.mMethodName !=null){
			url = this.mBaseUrl + this.mMethodName + "?";
		} else{
			url = this.mBaseUrl + "?";
		}						
		return url + mParamsUrlPart;
	}
}

