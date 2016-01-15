package github.com.foodactioncommitteecookbook.purchase;

import android.os.Bundle;

import github.com.foodactioncommitteecookbook.BaseActivity;
import github.com.foodactioncommitteecookbook.R;

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
