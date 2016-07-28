package com.douncoding.busnotifier.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.douncoding.busnotifier.R;

/**
 *
 */
public class NearStationView extends RelativeLayout implements View.OnClickListener {

    TextView mStationName;
    TextView mStationDistance;

    public NearStationView(Context context) {
        super(context);
        init();
    }

    public NearStationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_near_station_button, this);

        mStationDistance = (TextView)findViewById(R.id.near_station_distance);
        mStationName = (TextView)findViewById(R.id.near_station_name);

        /**
         * {@link NearStationView} 클릭 시 이벤트
         * 버튼과 같은 역할을 수행하기 위한 목적
         */
        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //TODO 주변정류장 표시하는 맵 출력
    }
}
