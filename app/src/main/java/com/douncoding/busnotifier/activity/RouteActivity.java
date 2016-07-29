package com.douncoding.busnotifier.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.view.StationRouteView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class RouteActivity extends BaseActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.station_route_view)
    StationRouteView mStationRouteView;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, RouteActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
    }
}
