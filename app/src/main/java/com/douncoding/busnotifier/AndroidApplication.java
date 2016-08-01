package com.douncoding.busnotifier;

import android.app.Application;
import android.util.Log;

import com.douncoding.busnotifier.data.repository.DatabaseContract;
import com.douncoding.busnotifier.data.repository.DatabaseHelper;
import com.douncoding.busnotifier.data.repository.RouteRepository;
import com.douncoding.busnotifier.data.repository.RouteStationRepository;
import com.douncoding.busnotifier.data.repository.StationRepository;

/**
 *
 */
public class AndroidApplication extends Application {

    RouteRepository routeRepository;
    StationRepository stationRepository;
    RouteStationRepository routeStationRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("CHECK", getDatabasePath(DatabaseContract.DATABASE_NAME).getAbsolutePath());

        routeRepository = RouteRepository.getInstance(this);
        routeRepository.createLocalDataStore();

        stationRepository = StationRepository.getInstance(this);
        stationRepository.createLocalDataStore();

        routeStationRepository = RouteStationRepository.getInstance(this);
        routeStationRepository.createLocalDataStore();

        DatabaseHelper.exportDatabse();
    }
}
