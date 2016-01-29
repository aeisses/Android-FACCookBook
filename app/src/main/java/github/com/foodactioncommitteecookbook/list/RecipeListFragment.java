package github.com.foodactioncommitteecookbook.list;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import github.com.foodactioncommitteecookbook.R;

/**
 *
 */
public class RecipeListFragment extends ListFragment {

  static RecipeListFragment newInstance() {
    RecipeListFragment fragment = new RecipeListFragment();
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
    ButterKnife.bind(this, view);

    Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.main_toolbar);
    toolbar.setTitle(R.string.browse_title);

    return view;
  }

}
