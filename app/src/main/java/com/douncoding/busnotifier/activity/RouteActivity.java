package com.douncoding.busnotifier.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.douncoding.busnotifier.NotifierService;
import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.data.BusLocation;
import com.douncoding.busnotifier.data.Route;
import com.douncoding.busnotifier.data.Station;
import com.douncoding.busnotifier.data.repository.BookmarkRepository;
import com.douncoding.busnotifier.data.repository.RouteStationRepository;
import com.douncoding.busnotifier.data.repository.StationRepository;
import com.douncoding.busnotifier.presenter.RouteContract;
import com.douncoding.busnotifier.presenter.RoutePresenter;
import com.douncoding.busnotifier.view.DestinationChooseDialog;
import com.douncoding.busnotifier.view.StationRouteView;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 프라그먼트 없이 액티비티 자체에서 기능 수행
 *
 * 기능목록:
 * - 노선정보
 * - 노선경로
 * - 현재 버스위치
 * - 하차알림 선택
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
    @BindView(R.id.bookmark_btn) ImageView mBookmarkButton;

    RoutePresenter mPresenter;

    public static final String EXTRA_ROUTE = "EXTRA_ROUTE";
    public static final String EXTRA_FLAG = "EXTRA_FLAG";
    public static final int FROM_SEARCH = 1;
    public static Intent getCallingIntent(Context context ,Route route, int flag) {
        if (route == null) {
            throw new IllegalArgumentException("Route 클래스를 찾을 수 없습니다");
        }

        Intent intent = new Intent(context, RouteActivity.class);
        intent.putExtra(EXTRA_ROUTE, route.toSerialize());
        intent.putExtra(EXTRA_FLAG, flag);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        ButterKnife.bind(this);

        // 비즈니스 로직 처리자 생성 및 초기화
        final Route route = new Gson().fromJson(getIntent().getStringExtra(EXTRA_ROUTE), Route.class);
        mPresenter = new RoutePresenter(
                route,
                this,
                StationRepository.getInstance(this),
                RouteStationRepository.getInstance(this),
                BookmarkRepository.getInstance(this));
        mPresenter.initialize();

        // 최근검색기록 출력 여부
        if (getIntent().getIntExtra(EXTRA_FLAG, 0) == FROM_SEARCH) {
            mPresenter.addRecent();
        }

        setSupportActionBar(mToolbar);
        // 경로보기 버튼 클릭
        mMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigator.navigateToMaps(RouteActivity.this);
            }
        });

        // 북마크 버튼 클릭
        mBookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.changeBookmark();
            }
        });

        // 알람 설정 버튼 클릭
        mStationRouteView.setOnListener(new StationRouteView.OnListener() {
            @Override
            public void onAlarmClicked(Station destStation, final Station targetStation, final String nearPlate) {
                DestinationChooseDialog dialog = DestinationChooseDialog.create(
                                                    destStation, targetStation, nearPlate);

                dialog.setOnChoosedListener(new DestinationChooseDialog.OnChoosedListener() {
                    @Override
                    public void onPositive() {
                        NotifierService.sendStartTrackBroadcast(getApplicationContext(),
                        route.getIdRoute(), targetStation.getIdStation(), nearPlate);
                    }
                });

                dialog.show(getSupportFragmentManager(), null);
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
    public void setRouteStationList(List<Station> slist, List<BusLocation> blist) {
        mStationRouteView.setUpStationList(slist, blist);
    }

    @Override
    public void showRouteDetailView(Route route) {

    }

    @Override
    public void showRouteStationMapView() {

    }

    @Override
    public void changeBookmarkState(boolean state) {
        if (state) {
            mBookmarkButton.setImageResource(R.drawable.ic_star_fill_24dp);
        } else {
            mBookmarkButton.setImageResource(R.drawable.ic_star_line_24dp);
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(RouteActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCurrentLocation() {

    }
}
