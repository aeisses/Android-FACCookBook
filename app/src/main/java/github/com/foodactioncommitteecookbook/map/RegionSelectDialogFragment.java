package github.com.foodactioncommitteecookbook.map;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import de.greenrobot.event.EventBus;

/**
 * Region Selection dialog fragment that is shown on the Map activity.
 */
public class RegionSelectDialogFragment extends DialogFragment {

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setSingleChoiceItems(getRegionAdapter(), 0, new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        EventBus.getDefault().post(new RegionSelectedEvent(Region.values()[which]));
        dismiss();
      }
    });
    return builder.create();
  }

  public ListAdapter getRegionAdapter() {
    ArrayAdapter<Region> adapter = new RegionAdapter(getActivity());
    adapter.addAll(Region.values());
    return adapter;
  }

  public static class RegionAdapter extends ArrayAdapter<Region> {

    public RegionAdapter(Context context) {
      super(context, android.R.layout.simple_list_item_single_choice);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      if (convertView == null) {
        convertView = LayoutInflater.from(this.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
      }

      Region region = getItem(position);
      TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
      textView.setText(this.getContext().getText(region.getResourceId()));

      return convertView;
    }
  }
}
