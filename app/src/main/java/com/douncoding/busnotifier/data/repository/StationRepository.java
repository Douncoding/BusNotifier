package com.douncoding.busnotifier.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.data.Route;
import com.douncoding.busnotifier.data.Station;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

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

    public interface OnListener {
        void onCommon();
    }

    public void createLocalDataStore(final OnListener onListener) {
        super.createLocalDataStore(R.raw.station, new BaseRepository.OnListener() {
            @Override
            public void onCreate() {
                if (onListener != null) {
                    onListener.onCommon();
                }
            }
        });
    }

    public List<Station> findStationById(int idStation) {
        String SELECT_SQL = String.format(Locale.KOREA, "SELECT * FROM %s WHERE %s = %d",
                super.TABLE_NAME, DatabaseContract.Station.STATION_ID, idStation);

        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_SQL, null);

        List<Station> results = new LinkedList<>();
        try {
            if (cursor.moveToFirst()) {
                do {
                    Station station = new Station();
                    station.setIdStation(cursor.getInt(
                            cursor.getColumnIndex(DatabaseContract.Station.STATION_ID)));
                    station.setName(cursor.getString(
                            cursor.getColumnIndex(DatabaseContract.Station.STATION_NM)));
                    station.setIdCenter(cursor.getInt(
                            cursor.getColumnIndex(DatabaseContract.Station.CENTER_ID)));
                    station.setIsCenter(cursor.getString(
                            cursor.getColumnIndex(DatabaseContract.Station.CENTER_YN)));
                    station.setX(cursor.getDouble(
                            cursor.getColumnIndex(DatabaseContract.Station.X)));
                    station.setY(cursor.getDouble(
                            cursor.getColumnIndex(DatabaseContract.Station.Y)));
                    station.setMobileCode(cursor.getString(
                            cursor.getColumnIndex(DatabaseContract.Station.MOBILE_NO)));
                    station.setRegionName(cursor.getString(
                            cursor.getColumnIndex(DatabaseContract.Station.REGION_NAME)));
                    results.add(station);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return results;
    }
}
