package github.com.foodactioncommitteecookbook;

import android.os.Bundle;

/**
 * Shows recipes available for purchase.
 */
public class PurchaseActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
    }
}
