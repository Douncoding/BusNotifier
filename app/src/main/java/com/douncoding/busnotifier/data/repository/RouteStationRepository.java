package com.douncoding.busnotifier.data.repository;

import android.content.Context;

import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.data.RouteStation;

/**
 *
 */
public class RouteStationRepository extends BaseRepository<RouteStation> {

    private static RouteStationRepository instance = null;

    public static RouteStationRepository getInstance(Context context) {
        if (instance == null) {
            instance = new RouteStationRepository(context);
        }
        return instance;
    }

    private RouteStationRepository(Context context) {
        super(context,
                DatabaseContract.RouteStation.TABLE_NAME,
                DatabaseContract.RouteStation.getColumnNames());
    }

    public void createLocalDataStore() {
        super.createLocalDataStore(R.raw.routestation);
    }


}
