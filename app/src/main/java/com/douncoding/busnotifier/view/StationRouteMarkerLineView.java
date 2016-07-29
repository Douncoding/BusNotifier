package com.douncoding.busnotifier.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.douncoding.busnotifier.R;

/**
 * 타임라인(노선정보)의 좌측 라인 및 마커 뷰
 * {@link StationRouteView}에 종속
 */
public class StationRouteMarkerLineView extends RelativeLayout {
    private static final String TAG = StationRouteMarkerLineView.class.getSimpleName();

    ImageView mMarkerView;

    public StationRouteMarkerLineView(Context context) {
        super(context);
        init();
    }

    public StationRouteMarkerLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_station_route_line, this);
        mMarkerView = (ImageView)findViewById(R.id.marker_icon);
    }
}
