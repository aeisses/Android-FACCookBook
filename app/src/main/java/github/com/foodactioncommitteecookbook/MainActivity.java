package github.com.foodactioncommitteecookbook;

import android.content.Intent;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import github.com.foodactioncommitteecookbook.map.MapActivity_;


/**
 * The main screen in the application.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

  @Click(R.id.button_temporary_for_maps)
  public void launchMaps() {
    startActivity(new Intent(this, MapActivity_.class));
  }

}
