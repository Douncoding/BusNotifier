package com.douncoding.busnotifier.data.repository;

import android.content.Context;

import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.data.Station;

/**
 *
 */
public class StationRepository extends BaseRepository<Station> {

    private static StationRepository instance = null;

    public static StationRepository getInstance(Context context) {
        if (instance == null) {
            instance = new StationRepository(context);
        }
        return instance;
    }

    private StationRepository(Context context) {
        super(context,
                DatabaseContract.Station.TABLE_NAME,
                DatabaseContract.Station.getColumnNames());
    }

    public void createLocalDataStore() {
        super.createLocalDataStore(R.raw.station);
    }
}
