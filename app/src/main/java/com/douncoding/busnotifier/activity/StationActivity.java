package com.douncoding.busnotifier.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.data.BusArrival;
import com.douncoding.busnotifier.data.Route;
import com.douncoding.busnotifier.data.Station;
import com.douncoding.busnotifier.data.repository.RouteRepository;
import com.douncoding.busnotifier.fragment.StationFragment;
import com.douncoding.busnotifier.presenter.StationContract;
import com.douncoding.busnotifier.presenter.StationPresenter;
import com.douncoding.busnotifier.view.StationArriveView;
import com.google.gson.Gson;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StationActivity extends BaseActivity implements StationContract.View {

    @BindView(R.id.maps_btn)
    LinearLayout mMapsButton;

    @BindView(R.id.content_container)
    LinearLayout mContentContainer;

    @BindView(R.id.station_code_txt) TextView mStationCode;
    @BindView(R.id.station_name_txt) TextView mStationName;
    @BindView(R.id.station_dir_txt) TextView mStationDir;

    StationArriveView mStationArriveView;
    StationPresenter mPresenter;

    private static final String EXTRA_STATION = "EXTRA_STATION";
    public static Intent getCallingIntent(Context context, Station station) {
        if (station == null) {
            throw new IllegalArgumentException("Station 클래스를 찾을수 없습니다");
        }

        Intent intent = new Intent(context, StationActivity.class);
        intent.putExtra(EXTRA_STATION, station.toSerialize());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);
        ButterKnife.bind(this);

        mPresenter = new StationPresenter(
                new Gson().fromJson(getIntent().getStringExtra(EXTRA_STATION), Station.class),
                this,
                RouteRepository.getInstance(this));
        mPresenter.initialize();

        mStationArriveView = new StationArriveView(this);
        mContentContainer.addView(mStationArriveView);

        mMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigator.navigateToMaps(StationActivity.this);
            }
        });
    }

    @Override
    public void setStationBasicInfo(Station station) {
        mStationCode.setText(station.getMobileCode());
        mStationName.setText(station.getName());
        mStationDir.setText(" ");
    }

    @Override
    public void showArrivalInfo(String type, HashMap<Route, BusArrival> map) {
        mStationArriveView.setDataStore(type, map);
    }

    @Override
    public void updateTime() {

    }
}
