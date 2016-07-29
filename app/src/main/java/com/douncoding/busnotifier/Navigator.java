package com.douncoding.busnotifier;

import android.content.Context;
import android.content.Intent;

import com.douncoding.busnotifier.activity.MapsActivity;
import com.douncoding.busnotifier.activity.RouteActivity;
import com.douncoding.busnotifier.activity.StationActivity;

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

    public void navigateToRoute(Context context) {
        if (context != null) {
            Intent intent = RouteActivity.getCallingIntent(context);
            context.startActivity(intent);
        }
    }

    public void navigateToStation(Context context) {
        if (context != null) {
            Intent intent = StationActivity.getCallingIntent(context);
            context.startActivity(intent);
        }
    }
}
