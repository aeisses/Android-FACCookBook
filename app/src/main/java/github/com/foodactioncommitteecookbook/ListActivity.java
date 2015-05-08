package github.com.foodactioncommitteecookbook;

import android.os.Bundle;

/**
 * Contains a list of recipes that can be selected.
 */
public class ListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }

}
