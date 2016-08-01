package com.douncoding.busnotifier.activity;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.douncoding.busnotifier.fragment.MainFragment;
import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.fragment.SearchFragment;
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
                addFragment(R.id.fragment_container, SearchFragment.getInstance(target));

                // 버튼 선택 시 키보드 제거
                invisibleVirtualKeyboard();
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


    /**
     * 포커스를 제거하여 가상키보드를 사라지게 한다.
     */
    private void invisibleVirtualKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
