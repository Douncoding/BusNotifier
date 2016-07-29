package com.douncoding.busnotifier.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 정류장 세부정보 화면
 * 1. 경유 버스정보 확인
 */
public class StationView extends RelativeLayout {

    public StationView(Context context) {
        super(context);
        init();
    }

    public StationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //inflate(getContext(), , this);
    }


}
