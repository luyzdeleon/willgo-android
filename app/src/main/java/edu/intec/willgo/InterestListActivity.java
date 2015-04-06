package edu.intec.willgo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;


/**
 * An activity representing a list of Interests. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link InterestDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link InterestListFragment} and the item details
 * (if present) is a {@link InterestDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link InterestListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class InterestListActivity extends FragmentActivity
        implements InterestListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_list);

        if (findViewById(R.id.interest_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((InterestListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.interest_list))
                    .setActivateOnItemClick(true);
        }
    }

    /**
     * Callback method from {@link InterestListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        Intent intent = new Intent(this, AddEditActivity.class);
        intent.putExtra(AddEditActivity.EXTRA_MESSAGE, id);
        startActivity(intent);
    }

    public void addInterestHandler(MenuItem item){
        Intent intent = new Intent(this, AddEditActivity.class);
        intent.putExtra(AddEditActivity.EXTRA_MESSAGE, "0");
        startActivity(intent);
    }

    public void exitHandler(MenuItem item){
        System.exit(0);
    }
}
