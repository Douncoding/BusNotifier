package com.douncoding.busnotifier.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.douncoding.busnotifier.R;
import com.douncoding.busnotifier.fragment.StationFragment;

public class StationActivity extends BaseActivity {

    public static Intent getCallingIntent(Context context) {
       return new Intent(context, StationActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        if (savedInstanceState == null) {
            addFragment(R.id.fragment_container, new StationFragment());
        }
    }
}
