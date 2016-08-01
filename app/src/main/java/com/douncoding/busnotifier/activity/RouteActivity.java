package com.douncoding.busnotifier.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.data.Route;
import com.douncoding.busnotifier.data.repository.RouteRepository;
import com.douncoding.busnotifier.data.repository.RouteStationRepository;
import com.douncoding.busnotifier.presenter.RouteContract;
import com.douncoding.busnotifier.presenter.RoutePresenter;
import com.douncoding.busnotifier.view.StationRouteView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class RouteActivity extends BaseActivity implements RouteContract.View {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.station_route_view) StationRouteView mStationRouteView;
    @BindView(R.id.route_name_txt) TextView mRouteNameText;
    @BindView(R.id.route_desc_txt) TextView mRouteDescText;
    @BindView(R.id.start_station) TextView mRouteStartStationText;
    @BindView(R.id.end_station) TextView mRouteEndStationText;
    @BindView(R.id.route_info_btn) LinearLayout mInfoButton;
    @BindView(R.id.maps_btn) LinearLayout mMapsButton;

     RoutePresenter mPresenter;

    public static final String EXTRA_ROUTE_NAME = "EXTRA_ROUTE_NAME";
    public static Intent getCallingIntent(Context context ,String routeName) {
        if (routeName == null) {
            throw new IllegalArgumentException("노선이름을 입력해야 합니다.");
        }

        Intent intent = new Intent(context, RouteActivity.class);
        intent.putExtra(EXTRA_ROUTE_NAME, routeName);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        ButterKnife.bind(this);

        // 비즈니스 로직 처리자 생성 및 초기화
        mPresenter = new RoutePresenter(
                getIntent().getStringExtra(EXTRA_ROUTE_NAME),
                this,
                RouteRepository.getInstance(this),
                RouteStationRepository.getInstance(this));
        mPresenter.initialize();

        setSupportActionBar(mToolbar);

        mMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigator.navigateToMaps(RouteActivity.this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void setRouteBasicInfo(Route route) {
        mRouteNameText.setText(route.getRouteName());
        mRouteDescText.setText(route.getDescription());
        mRouteStartStationText.setText(route.getStartStationName());
        mRouteEndStationText.setText(route.getEndStationName());
    }

    @Override
    public void setRouteStationList() {

    }

    @Override
    public void showRouteDetailView(Route route) {

    }

    @Override
    public void showRouteStationMapView() {

    }

    @Override
    public void toggleBookmarkButton(boolean state) {

    }

    @Override
    public void showCurrentLocation() {

    }
}
