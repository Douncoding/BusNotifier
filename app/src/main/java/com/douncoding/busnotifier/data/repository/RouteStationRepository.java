package com.douncoding.busnotifier.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.data.RouteStation;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

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

    public List<RouteStation> findRouteStationById(int idRoute) {
        String SELECT_SQL = String.format(Locale.KOREA, "SELECT * FROM %s WHERE %s = %d",
                super.TABLE_NAME, DatabaseContract.RouteStation.ROUTE_ID, idRoute);

        SQLiteDatabase db = super.mDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_SQL, null);

        List<RouteStation> results = new LinkedList<>();
        try {
            if (cursor.moveToFirst()) {
                do {
                    RouteStation routeStation = new RouteStation();
                    routeStation.setIdRoute(cursor.getInt(
                            cursor.getColumnIndex(DatabaseContract.RouteStation.ROUTE_ID)));
                    routeStation.setIdStation(cursor.getInt(
                            cursor.getColumnIndex(DatabaseContract.RouteStation.STATION_ID)));
                    routeStation.setRouteName(cursor.getString(
                            cursor.getColumnIndex(DatabaseContract.RouteStation.ROUTE_NM)));
                    routeStation.setStationName(cursor.getString(
                            cursor.getColumnIndex(DatabaseContract.RouteStation.STATION_NM)));
                    routeStation.setStationOrder(cursor.getInt(
                            cursor.getColumnIndex(DatabaseContract.RouteStation.STA_ORDER)));
                    routeStation.setUpDown(cursor.getString(
                            cursor.getColumnIndex(DatabaseContract.RouteStation.UPDOWN)));
                    results.add(routeStation);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }

        return results;
    }
}
