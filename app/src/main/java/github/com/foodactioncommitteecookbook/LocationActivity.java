package github.com.foodactioncommitteecookbook;

import android.os.Bundle;

/**
 * Displays information about a selected local food location.
 */
public class LocationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
    }

}
