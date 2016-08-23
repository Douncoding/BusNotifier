package com.douncoding.busnotifier;

import android.content.Context;
import android.content.Intent;

import com.douncoding.busnotifier.activity.MainActivity;
import com.douncoding.busnotifier.activity.MapsActivity;
import com.douncoding.busnotifier.activity.RouteActivity;
import com.douncoding.busnotifier.activity.StationActivity;
import com.douncoding.busnotifier.data.Route;
import com.douncoding.busnotifier.data.Station;

/**
 * 액티비티 간의 이동을 관리하는 클래스
 */
public class Navigator {

    public Navigator() {}

    public void navigateToMaps(Context context) {
        if (context != null) {
            Intent intent = MapsActivity.getCallingIntent(context);
            context.startActivity(intent);
        }
    }

    public static void navigateToMain(Context context) {
        if (context != null) {
            Intent intent = MainActivity.getCallingIntent(context);
            context.startActivity(intent);
        }
    }

    public static void startNotifierService(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, NotifierService.class);
            context.startService(intent);
        }
    }

    /**
     *
     * @param context 현재 컨택스트
     * @param route 조회 노선
     * @param flag 최근검색목록 기록 여부
     */
    public static void navigateToRoute(Context context, Route route, int flag) {
        if (context != null) {
            Intent intent = RouteActivity.getCallingIntent(context, route, flag);
            context.startActivity(intent);
        }
    }

    public static void navigateToStation(Context context, Station station) {
        if (context != null) {
            Intent intent = StationActivity.getCallingIntent(context, station);
            context.startActivity(intent);
        }
    }
}
