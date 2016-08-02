package com.douncoding.busnotifier.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.douncoding.busnotifier.R;

/**
 * 타임라인(노선정보)의 좌측 라인 및 마커 뷰
 * {@link StationRouteView}에 종속
 */
public class StationRouteMarkerLineView extends RelativeLayout {
    private static final String TAG = StationRouteMarkerLineView.class.getSimpleName();

    ImageView mMarkerView;

    ViewGroup mBusLocationView;
    TextView mBusPlateText;

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
        mBusLocationView = (ViewGroup)findViewById(R.id.bus_location_view);
        mBusPlateText = (TextView)findViewById(R.id.bus_number_txt);
    }

    public void visible(String plate) {
        mBusLocationView.setVisibility(VISIBLE);
        mBusPlateText.setText(plate);
    }

    public void invisible() {
        mBusLocationView.setVisibility(INVISIBLE);
        mBusPlateText.setText(" ");
    }
}
