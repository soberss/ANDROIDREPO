package controllers;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import dataproviding.BitmapDataProvider;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

public class ImageLoader {
	private static ImageLoader sInstance;
	private HashMap<String, SoftReference<Bitmap>> mImageCache = null;
		
	private ImageLoader(){
		mImageCache = new HashMap<String, SoftReference<Bitmap>>();
	}
	
	public static ImageLoader getInstance(){
		if(sInstance == null) sInstance = new ImageLoader();				
		return sInstance;
	}
	
	public Bitmap loadImage(final String url, final ResultHandler resultHandler){		
		if (mImageCache.containsKey(url)) {
            SoftReference<Bitmap> softReference = mImageCache.get(url);
            Bitmap bitmap = softReference.get();
            if (bitmap != null) {
                return bitmap;
            }
        }
				
		final Handler handler = new Handler() {			
            @Override
            public void handleMessage(Message message) {
            	resultHandler.loadingComplete((Bitmap)message.obj, url);
            }
        };
        
		new Thread() {
            @Override
            public void run() {
            	BitmapDataProvider bdp = new BitmapDataProvider(url);
            	bdp.run();
            	Bitmap b = bdp.getResult();            	
                mImageCache.put(url, new SoftReference<Bitmap>(b));
                Message message = handler.obtainMessage(0, b);
                handler.sendMessage(message);
            }
        }.start();		
		return null;	
	}
	
	public interface ResultHandler{
		public void loadingComplete(Bitmap bitmap, String url);
	}
	
	public void clearPhotosCache(){
		mImageCache.clear();
	}
}
