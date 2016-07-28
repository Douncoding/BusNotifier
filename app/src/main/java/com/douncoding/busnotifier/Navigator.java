package com.douncoding.busnotifier;

import android.content.Context;
import android.content.Intent;

import com.douncoding.busnotifier.activity.MapsActivity;

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
}
