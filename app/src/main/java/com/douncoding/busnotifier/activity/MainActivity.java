package com.douncoding.busnotifier.activity;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.douncoding.busnotifier.data.Route;
import com.douncoding.busnotifier.fragment.MainFragment;
import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.data.repository.RouteRepository;
import com.douncoding.busnotifier.view.SearchView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 메인 화면
 *
 */
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.drawer_layout) DrawerLayout mDrawer;
    @BindView(R.id.nav_view) NavigationView mNavView;
    @BindView(R.id.search_view) SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupActionBar();
        if (savedInstanceState == null) {
            addFragment(R.id.fragment_container, new MainFragment());
        }
    }

    /**
     * 액션바 관련 설정
     */
    private void setupActionBar() {
        setSupportActionBar(mToolbar);

        /**
         * {@link NavigationView} 설정
         */
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);

        /**
         * {@link SearchView} 설정
         */
        mSearchView.setOnListener(new SearchView.OnListener() {
            @Override
            public void onSearchClick(View view, String target) {
                //TODO 검색기능
                showToastMessage("검색: " + target);
                List<Route> results =
                        RouteRepository.getInstance(MainActivity.this).findByLikeName(target);
                StringBuilder builder = new StringBuilder();
                for (Route route : results) {
                    builder.append(route.getRouteName()).append("\n");
                }
                Log.e("CHECK", builder.toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
