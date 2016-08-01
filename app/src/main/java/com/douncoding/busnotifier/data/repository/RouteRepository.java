package com.douncoding.busnotifier.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.data.Route;
import java.util.LinkedList;
import java.util.List;

public class RouteRepository extends BaseRepository<Route> {
    private static RouteRepository instance = null;

    private RouteRepository(Context context) {
        super(context,
                DatabaseContract.Route.TABLE_NAME,
                DatabaseContract.Route.getColumnNames());
    }

    public static RouteRepository getInstance(Context context) {
        if (instance == null) {
            instance = new RouteRepository(context);
        }
        return instance;
    }

    public void createLocalDataStore() {
        super.createLocalDataStore(R.raw.station);
    }

    /**
     * @param routeName 노선번호
     * @return 입력된 노선번호가 포함된 모든 노선 목록
     */
    public List<Route> findByLikeName(String routeName) {
        String SELECT_SQL = String.format("SELECT * FROM %s WHERE %s = %s",
                TABLE_NAME, DatabaseContract.Route.ROUTE_NM, routeName);
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_SQL, null);

        List<Route> results = new LinkedList<>();
        try {
            if (cursor.moveToFirst()) {
                do {
                    Route route = new Route();
                    route.setIdRoute(cursor.getInt(
                            cursor.getColumnIndex(DatabaseContract.Route.ROUTE_ID)));
                    route.setRouteName(cursor.getString(
                            cursor.getColumnIndex(DatabaseContract.Route.ROUTE_NM)));
                    route.setRouteType(cursor.getInt(
                            cursor.getColumnIndex(DatabaseContract.Route.ROUTE_TP)));
                    route.setStartStationId(cursor.getInt(
                            cursor.getColumnIndex(DatabaseContract.Route.ST_STA_ID)));
                    route.setStartStationName(cursor.getString(
                            cursor.getColumnIndex(DatabaseContract.Route.ST_STA_NM)));
                    route.setStartStationCode(cursor.getString(
                            cursor.getColumnIndex(DatabaseContract.Route.ST_STA_NO)));
                    route.setEndStationId(cursor.getInt(
                            cursor.getColumnIndex(DatabaseContract.Route.ED_STA_ID)));
                    route.setEndStationName(cursor.getString(
                            cursor.getColumnIndex(DatabaseContract.Route.ED_STA_NM)));
                    route.setEndStationCode(cursor.getString(
                            cursor.getColumnIndex(DatabaseContract.Route.ED_STA_NO)));
                    route.setUpFirstTime(cursor.getString(
                            cursor.getColumnIndex(DatabaseContract.Route.UP_FIRST_TIME)));
                    route.setUpLastTime(cursor.getString(
                            cursor.getColumnIndex(DatabaseContract.Route.UP_LAST_TIME)));
                    route.setDownFirstTime(cursor.getString(
                            cursor.getColumnIndex(DatabaseContract.Route.DOWN_FIRST_TIME)));
                    route.setDownLastTime(cursor.getString(
                            cursor.getColumnIndex(DatabaseContract.Route.DOWN_LAST_TIME)));
                    route.setPeekAlloc(cursor.getString(
                            cursor.getColumnIndex(DatabaseContract.Route.PEEK_ALLOC)));
                    route.setNonPeekAlloc(cursor.getString(
                            cursor.getColumnIndex(DatabaseContract.Route.NPEEK_ALLOC)));
                    route.setRegionName(cursor.getString(
                            cursor.getColumnIndex(DatabaseContract.Route.REGION_NAME)));
                    results.add(route);
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
