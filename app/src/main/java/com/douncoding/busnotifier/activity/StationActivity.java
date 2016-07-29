package com.douncoding.busnotifier.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.fragment.StationFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StationActivity extends BaseActivity {

    @BindView(R.id.maps_btn)
    LinearLayout mMapsButton;

    public static Intent getCallingIntent(Context context) {
       return new Intent(context, StationActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            addFragment(R.id.fragment_container, new StationFragment());
        }

        mMapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNavigator.navigateToMaps(StationActivity.this);
            }
        });
    }
}
