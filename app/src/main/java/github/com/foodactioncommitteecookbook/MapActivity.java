package github.com.foodactioncommitteecookbook;

import android.os.Bundle;

/**
 * Shows a map of the province with local food sources annotated.
 */
public class MapActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }


}
