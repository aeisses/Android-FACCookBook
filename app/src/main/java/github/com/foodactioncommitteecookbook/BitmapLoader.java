package github.com.foodactioncommitteecookbook;


import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import timber.log.Timber;

public class BitmapLoader {
  static public void loadImageViewBitmap(Context context, ImageView view, Uri bitmapUri) {

    try {
      Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), bitmapUri);
      view.setImageBitmap(bitmap);
    } catch (Exception e) {
      Timber.w("Unable to load image from Uri: {}.\n{}", bitmapUri, e);
    }
  }
}
