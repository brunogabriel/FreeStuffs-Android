package br.com.friendlydonations.views.activities;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import br.com.friendlydonations.R;
import br.com.friendlydonations.managers.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.dynamicbox.DynamicBox;

/**
 * Created by brunogabriel on 13/11/16.
 */

public class FavouritesActivity extends BaseActivity {

    // Constants
    private static final String DYNAMICBOX_FAVOURITES_EMPTY = "dynamicboxfavouritesempty";

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;

    private DynamicBox dynamicBox;
    private StaggeredGridLayoutManager gridLayoutManager;
    private View dynamicBoxFavouritesEmptyView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        ButterKnife.bind(this);
        initUI();
    }

    @Override
    public void initUI() {
        setupToolbar(toolbar, getString(R.string.favourites_title), null, true, true);
        dynamicBoxFavouritesEmptyView = LayoutInflater.from(this).inflate(R.layout.dynamicbox_favourites_empty, null, false);
        gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        dynamicBox = new DynamicBox(this, recyclerView);
        dynamicBox.addCustomView(dynamicBoxFavouritesEmptyView, DYNAMICBOX_FAVOURITES_EMPTY);
        dynamicBox.showCustomView(DYNAMICBOX_FAVOURITES_EMPTY);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
