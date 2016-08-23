package com.douncoding.busnotifier.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.douncoding.busnotifier.Navigator;
import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.data.repository.DatabaseHelper;
import com.douncoding.busnotifier.data.repository.RouteRepository;
import com.douncoding.busnotifier.data.repository.RouteStationRepository;
import com.douncoding.busnotifier.data.repository.StationRepository;

public class SplashActivity extends BaseActivity {

    RouteRepository routeRepository;
    StationRepository stationRepository;
    RouteStationRepository routeStationRepository;

    enum Status {ROUTE, STATION, ROUTESTATION}
    boolean[] isFinishedLoads = new boolean[Status.values().length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initialize();
    }

    private void initialize() {
        routeRepository = RouteRepository.getInstance(this);
        routeRepository.createLocalDataStore(new RouteRepository.OnListener() {
            @Override
            public void onCommon() {
                checkDatabaseStatus(Status.ROUTE);
            }
        });

        stationRepository = StationRepository.getInstance(this);
        stationRepository.createLocalDataStore(new StationRepository.OnListener() {
            @Override
            public void onCommon() {
                checkDatabaseStatus(Status.STATION);
            }
        });

        routeStationRepository = RouteStationRepository.getInstance(this);
        routeStationRepository.createLocalDataStore(new RouteStationRepository.OnListener() {
            @Override
            public void onCommon() {
                checkDatabaseStatus(Status.ROUTESTATION);
            }
        });
    }

    private void checkDatabaseStatus(Status status) {
        boolean isFinished = true;

        switch (status) {
            case ROUTE:
                isFinishedLoads[0] = true;
                break;
            case STATION:
                isFinishedLoads[1] = true;
                break;
            case ROUTESTATION:
                isFinishedLoads[2] = true;
                break;
        }

        for (boolean item : isFinishedLoads) {
            isFinished = item;
        }

          if (isFinished) {
            // 테스트 목적 생성된 데이터베이스 '다운로드' 디렉터리 이동
            DatabaseHelper.exportDatabase();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Navigator.startNotifierService(getApplicationContext());
                    Navigator.navigateToMain(SplashActivity.this);
                    finish();
                }
            }, 2000);
        }
    }
}
