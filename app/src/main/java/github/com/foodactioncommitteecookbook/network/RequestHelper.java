package github.com.foodactioncommitteecookbook.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Manages the Volley request queue.
 */
public class RequestHelper {

    private static final int MAX_IMAGE_CACHE_ENTIRES  = 100;

    private static RequestHelper INSTANCE;

    public static RequestHelper create(final Context context) {
        INSTANCE = new RequestHelper(context);
        return INSTANCE;
    }

    public static RequestHelper instance() {
        return INSTANCE;
    }
    public static ImageLoader imageLoader() {
        return INSTANCE.imageLoader;
    }


    private final RequestQueue queue;
    private static ImageLoader imageLoader;

    private RequestHelper(final Context context) {
        queue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(queue, new BitmapCache(MAX_IMAGE_CACHE_ENTIRES));
    }

    public void send(final Request<?> request) {
        queue.add(request);
    }
}
