package dataproviding;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

/**gets bitmap with async loading from url to its inner field to be requested */
public class BitmapDataProvider extends UrlDataProvider  {
	private final static String TAG = "BitmapDataProvider";
	private Bitmap mBitmap;
	
	public BitmapDataProvider(String url) {
		super(url);		
	}
	
	@Override
	public void run() {		
		Bitmap bitmap = null;
		InputStream is;
		try {
			is = createInputStreamFromUrl();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}																	
        mBitmap = bitmap;		
	}

	@Override
	public Bitmap getResult() {		
		return mBitmap;
	}
	
	
}
