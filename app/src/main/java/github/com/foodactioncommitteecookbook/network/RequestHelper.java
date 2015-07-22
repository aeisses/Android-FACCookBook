package github.com.foodactioncommitteecookbook.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Manages the Volley request queue.
 */
public class RequestHelper {

    private static RequestHelper INSTANCE;

    public static RequestHelper create(final Context context) {
        INSTANCE = new RequestHelper(context);
        return INSTANCE;
    }

    public static RequestHelper instance() {
        return INSTANCE;
    }

    private final RequestQueue queue;

    private RequestHelper(final Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public void send(final Request<?> request) {
        queue.add(request);
    }
}
