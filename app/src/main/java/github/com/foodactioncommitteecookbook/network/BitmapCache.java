package github.com.foodactioncommitteecookbook.network;


import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

public class BitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {
  public BitmapCache (int maxSize) {
    super(maxSize);
  }

  @Override
  public Bitmap getBitmap (String url) {
    return get(url);
  }

  @Override
  public void putBitmap (String url, Bitmap bitmap) {
    put(url, bitmap);
  }
}
